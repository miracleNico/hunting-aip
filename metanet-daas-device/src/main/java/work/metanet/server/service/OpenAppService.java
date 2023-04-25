package work.metanet.server.service;

import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import work.metanet.api.openApp.IOpenAppService;
import work.metanet.api.openApp.protocol.ReqOpenAppList;
import work.metanet.api.openApp.protocol.ReqOpenAppList.RespOpenAppList;
import work.metanet.api.openApp.protocol.ReqOpenAppRemove;
import work.metanet.api.openApp.protocol.ReqOpenAppSave;
import work.metanet.base.RespPaging;
import work.metanet.constant.ConstCacheKey;
import work.metanet.exception.DaasException;
import work.metanet.model.OpenApp;
import work.metanet.server.dao.OpenAppMapper;
import work.metanet.utils.LxKeyUtil;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;

@DubboService
public class OpenAppService implements IOpenAppService{

	@Autowired
	private OpenAppMapper openAppMapper;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Override
	public OpenApp getOpenAppByName(String channelId,String appName) throws Exception{
		OpenApp openApp = openAppMapper.selectOne(new OpenApp().setStatus(true).setChannelId(channelId).setAppName(appName));
		if(BeanUtil.isEmpty(openApp)) throw DaasException.of().setMsg("应用不存在");
		return openApp;
	}
	
	@Override
	public OpenApp openApp(String appId) throws Exception {
		String key = ConstCacheKey.OPEN_APP.cacheKey();
		OpenApp openApp = JSONUtil.toBean(Convert.toStr(stringRedisTemplate.opsForHash().get(key, appId)), OpenApp.class);
		if(StrUtil.isBlank(openApp.getAppId())) {
			openApp = openAppMapper.selectOne(new OpenApp().setAppId(appId).setStatus(true));
			if(BeanUtil.isNotEmpty(openApp)) {
				stringRedisTemplate.opsForHash().put(key, appId, JSONUtil.toJsonStr(openApp));
			}else {
				throw DaasException.of().setMsg("应用不存在");
			}
		}
		return openApp;
	}
	
	@Override
	public List<OpenApp> appSelected(Map<String, Object> map) {
		return openAppMapper.appSelected(map);
	}
	
	@Override
	public RespPaging<RespOpenAppList> appList(ReqOpenAppList req) throws Exception {
		RespPaging<RespOpenAppList> respPaging = new RespPaging<RespOpenAppList>();
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<RespOpenAppList> list = openAppMapper.appList(req);
		BeanUtil.copyProperties(new PageInfo<RespOpenAppList>(list), respPaging);
		return respPaging;
	}
	
	@Override
	public OpenApp info(String appId) throws Exception {
		return openAppMapper.selectByPrimaryKey(appId);
	}
	
	@Override
	public void saveApp(ReqOpenAppSave req) throws Exception {
		OpenApp openApp = openAppMapper.selectOne(new OpenApp().setStatus(true).setChannelId(req.getChannelId()).setAppName(req.getAppName()));
		if(StrUtil.isBlank(req.getAppId())) {
			if(BeanUtil.isNotEmpty(openApp)) throw DaasException.of().setMsg("应用已存在");
			openApp = BeanUtil.copyProperties(req, OpenApp.class);
			openApp.setAppId(IdUtil.objectId()).setAppKey(LxKeyUtil.appSecret());
			openAppMapper.insertSelective(openApp);
		}else {
			OpenApp db = openAppMapper.selectByPrimaryKey(req.getAppId());
			if(BeanUtil.isNotEmpty(openApp) && !StrUtil.equals(openApp.getAppId(), db.getAppId())) throw DaasException.of().setMsg("应用已存在");
			BeanUtil.copyProperties(req, db, CopyOptions.create().ignoreNullValue());
			openAppMapper.updateByPrimaryKeySelective(db);
			openApp = db;
		}
		stringRedisTemplate.opsForHash().put(ConstCacheKey.OPEN_APP.cacheKey(), openApp.getAppId(), JSONUtil.toJsonStr(openApp));
	}
	
	@Override
	public void removeApp(List<ReqOpenAppRemove> req) throws Exception {
		openAppMapper.removeApp(req);
		for (ReqOpenAppRemove app : req) {
			stringRedisTemplate.opsForHash().delete(ConstCacheKey.OPEN_APP.cacheKey(), app.getAppId());
		}
	}
	
}
