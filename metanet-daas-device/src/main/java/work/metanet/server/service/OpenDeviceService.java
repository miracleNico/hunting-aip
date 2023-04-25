package work.metanet.server.service;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.integration.redis.util.RedisLockRegistry;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import work.metanet.api.openApp.IOpenAppService;
import work.metanet.api.openDevice.IOpenDeviceService;
import work.metanet.api.openDevice.protocol.ReqOpenDeviceAuth;
import work.metanet.api.openDevice.protocol.ReqOpenDeviceAuth.RespOpenDeviceAuth;
import work.metanet.api.openDevice.protocol.ReqOpenDeviceEnable;
import work.metanet.api.openDevice.protocol.ReqOpenDeviceList;
import work.metanet.api.openDevice.protocol.ReqOpenDeviceList.RespOpenDeviceList;
import work.metanet.api.openDevice.protocol.ReqOpenDeviceRemove;
import work.metanet.api.openDevice.vo.OpenDeviceToken;
import work.metanet.base.RespPaging;
import work.metanet.base.ResultMessage;
import work.metanet.constant.ConstCacheKey;
import work.metanet.constant.Constant;
import work.metanet.exception.DaasException;
import work.metanet.model.OpenApp;
import work.metanet.model.OpenDevice;
import work.metanet.server.dao.OpenDeviceMapper;
import work.metanet.utils.token.JwtUtil;
import work.metanet.utils.token.TokenUtil;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DubboService
public class OpenDeviceService implements IOpenDeviceService{

	@Autowired
	private IOpenAppService openAppService;
	@Autowired
	private OpenDeviceMapper openDeviceMapper;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private Constant constant;
	@Autowired
    private RedisLockRegistry redisLockRegistry;
	
	
	public String checkToken(String token,OpenDeviceToken openDeviceToken) throws Exception {
		String refreshToken = null;
		String appId = openDeviceToken.getAppId();
        String deviceId = openDeviceToken.getDeviceId();
        Lock lock = redisLockRegistry.obtain(appId+deviceId);
        String id = IdUtil.objectId();
    	if(lock.tryLock(constant.getRedis_lock_timeout_seconds(),TimeUnit.SECONDS)) {
    		//log.info("---{} 我拿到了锁---:{}",id,lock.toString());
    		try {
    			//token是否存在
    			Boolean existCache = existCacheKey(appId, deviceId);
    			if(!existCache) throw DaasException.of().setResult(ResultMessage.FAILURE_OPENAPI_AUTH.result());
    			
    			String cacheToken = cacheToken(appId, deviceId);
    			Boolean checkJWT = JwtUtil.checkJWT(token);
    			
    			//认证成功
    			if(checkJWT && token.equals(cacheToken)) return refreshToken;
    			
    			//并发情况匹配旧token 认证成功
    			if(token.equals(cacheOldToken(appId, deviceId))) return refreshToken;
    			
    			//token过期刷新
    			if(!checkJWT && token.equals(cacheToken)) {
    				openDeviceToken.setRefresh(true);
    				refreshToken = generateToken(openDeviceToken);
    				cacheToken(appId, deviceId, refreshToken);
    				cacheOldToken(appId, deviceId, token);
    				return refreshToken;
    			}
    			
    			//重复设备在另一端认证
    			if(checkJWT && !token.equals(cacheToken)) throw DaasException.of().setResult(ResultMessage.FAILURE_OPENAPI_AUTH_OTHER_CLIENT.result());
    			//未使用刷新后的token
    			if(!checkJWT && !token.equals(cacheToken)) throw DaasException.of().setResult(ResultMessage.FAILURE_OPENAPI_AUTH_REFRESH_TOKEN.result());
    			throw DaasException.of().setResult(ResultMessage.FAILURE_OPENAPI_AUTH.result());
    		}finally {
    			//log.info("---{} 我释放了锁---:{}",id,lock.toString());
    			lock.unlock();
    		}
    	}else {
    		log.info("---{} 我等待了{}秒还未拿到锁---:{}",id,constant.getRedis_lock_timeout_seconds(),lock.toString());
    		throw DaasException.of().setMsg("服务器繁忙！");
    	}
	}
	
	@Override
	public RespPaging<RespOpenDeviceList> deviceList(ReqOpenDeviceList req) throws Exception {
		RespPaging<RespOpenDeviceList> respPaging = new RespPaging<RespOpenDeviceList>();
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<RespOpenDeviceList> list = openDeviceMapper.deviceList(req);
		BeanUtil.copyProperties(new PageInfo<RespOpenDeviceList>(list),respPaging);
		return respPaging;
	}
	
	@Override
	public void removeDevice(List<ReqOpenDeviceRemove> devices) throws Exception {
		for (ReqOpenDeviceRemove device : devices) {
			OpenDevice openDevice = openDeviceMapper.selectByPrimaryKey(device.getOpenDeviceId());
			if(BeanUtil.isNotEmpty(openDevice)) {
				openDeviceMapper.updateByPrimaryKeySelective(openDevice.setStatus(false));
				delCache(openDevice.getAppId(), openDevice.getDeviceId());
			}
		}
	}
	
	@Override
	public OpenDevice info(String openDeviceId) throws Exception {
		return openDeviceMapper.selectByPrimaryKey(openDeviceId);
	}
	
	@Override
	public void enable(ReqOpenDeviceEnable req) throws Exception {
		openDeviceMapper.updateByPrimaryKeySelective(new OpenDevice().setOpenDeviceId(req.getOpenDeviceId()).setEnable(req.getEnable()).setRemark(req.getRemark()));
		if(!req.getEnable()) {
			OpenDevice opDevice = openDeviceMapper.selectByPrimaryKey(req.getOpenDeviceId());
			delCache(opDevice.getAppId(), opDevice.getDeviceId());
		}
	}
	
	private void delCache(String appId,String deviceId) {
		stringRedisTemplate.delete(ConstCacheKey.OPEN_TOKEN.cacheKey(appId,deviceId));
		stringRedisTemplate.delete(ConstCacheKey.OPEN_AUTH_NUMBER.cacheKey(appId,deviceId));
	}
	
	private Boolean existCacheKey(String appId,String deviceId) {
		String key = ConstCacheKey.OPEN_TOKEN.cacheKey(appId,deviceId);
		return stringRedisTemplate.hasKey(key);
	}

	private String cacheToken(String appId,String deviceId) {
		String key = ConstCacheKey.OPEN_TOKEN.cacheKey(appId,deviceId);
		return stringRedisTemplate.opsForValue().get(key);
	}

	private void cacheToken(String appId,String deviceId, String token) {
		String key = ConstCacheKey.OPEN_TOKEN.cacheKey(appId,deviceId);
		stringRedisTemplate.opsForValue().set(key, token,Duration.ofDays(constant.getOpenapi_token_server_cache_timeout_day()));
	}
	
	private String cacheOldToken(String appId,String deviceId) {
		String key = ConstCacheKey.OPEN_OLD_TOKEN.cacheKey(appId,deviceId);
		return stringRedisTemplate.opsForValue().get(key);
	}

	private void cacheOldToken(String appId,String deviceId, String token) {
		String key = ConstCacheKey.OPEN_OLD_TOKEN.cacheKey(appId,deviceId);
		stringRedisTemplate.opsForValue().set(key, token,Duration.ofSeconds(ConstCacheKey.OPEN_OLD_TOKEN.getExpire()));
	}
	
	private String generateToken(OpenDeviceToken openDeviceToken) {
		return TokenUtil.generateToken(JSONUtil.toJsonStr(openDeviceToken), ConstCacheKey.OPEN_TOKEN.getExpire());
	}
	
	@Override
	public RespOpenDeviceAuth auth(ReqOpenDeviceAuth req) throws Exception {
		String appId = req.getAppId();
		String deviceId = req.getDeviceId();
		ConstCacheKey constCacheOpenAuthNumberKey = ConstCacheKey.OPEN_AUTH_NUMBER;
		String cacheOpenAuthNumberKey = constCacheOpenAuthNumberKey.cacheKey(appId,deviceId);
		
		//检查认证次数
		Integer authNumber = Convert.toInt(stringRedisTemplate.opsForValue().get(cacheOpenAuthNumberKey), 0);
		if(authNumber >= constant.getOpenapi_device_24h_max_auth_number()) throw DaasException.of().setResult(ResultMessage.FAILURE_OPENAPI_AUTH_24H_MAX_NUMBER.result());
		
		//获取应用与设备信息
		OpenApp openApp = openAppService.openApp(appId);
		OpenDevice openDevice = openDeviceMapper.selectOne(new OpenDevice().setAppId(appId).setDeviceId(deviceId).setStatus(true));
		if(BeanUtil.isNotEmpty(openDevice)) {
			if(!openDevice.getEnable()) throw DaasException.of().setResult(ResultMessage.FAILURE_DEVICE_DISABLE.result());
		}else {
			Integer openDeviceNumber = openDeviceMapper.selectCount(new OpenDevice().setAppId(appId).setStatus(true));
			if(openDeviceNumber >= openApp.getSignDeviceNumber()) throw DaasException.of().setResult(ResultMessage.FAILURE_OPENAPI_AUTH_SIGN_MAX_NUMBER.result());
			openDevice = BeanUtil.copyProperties(req, OpenDevice.class).setOpenDeviceId(IdUtil.fastSimpleUUID()).setAppId(appId).setDeviceId(deviceId);
			openDeviceMapper.insertSelective(openDevice);
		}
		
		//生成token
		OpenDeviceToken openDeviceToken = new OpenDeviceToken(openDevice.getOpenDeviceId(), deviceId, appId, openApp.getChannelId(),false);
		String token = generateToken(openDeviceToken);
		
		//缓存token
		cacheToken(openDeviceToken.getAppId(), openDeviceToken.getDeviceId(), token);
		
		//缓存认证次数
		stringRedisTemplate.opsForValue().set(cacheOpenAuthNumberKey, Convert.toStr(NumberUtil.add(authNumber, Integer.valueOf(1))), Duration.ofSeconds(constCacheOpenAuthNumberKey.getExpire()));
		
		RespOpenDeviceAuth resp = new RespOpenDeviceAuth().setToken(token);

		return resp;
	}
	
	
	
}
