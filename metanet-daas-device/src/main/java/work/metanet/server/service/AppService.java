package work.metanet.server.service;


import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import work.metanet.api.app.IAppService;
import work.metanet.api.app.protocol.ReqAppInfo;
import work.metanet.api.app.protocol.ReqAppInfo.RespAppInfo;
import work.metanet.api.app.protocol.ReqAppList;
import work.metanet.api.app.protocol.ReqAppList.RespAppList;
import work.metanet.api.app.protocol.ReqEnableSn;
import work.metanet.api.app.protocol.ReqRemoveApp;
import work.metanet.api.app.protocol.ReqSaveApp;
import work.metanet.api.app.vo.AppEarningsSettingVo;
import work.metanet.api.app.vo.AppKeyVo;
import work.metanet.api.app.vo.AppVo;
import work.metanet.api.upgradePlan.protocol.ReqUpgrade;
import work.metanet.api.upgradePlan.protocol.ReqUpgrade.RespUpgrade;
import work.metanet.base.RespPaging;
import work.metanet.base.ResultMessage;
import work.metanet.constant.ConstAppType;
import work.metanet.constant.ConstCacheKey;
import work.metanet.constant.ConstDeviceAuthType;
import work.metanet.constant.ConstUrlType;
import work.metanet.exception.DaasException;
import work.metanet.model.App;
import work.metanet.server.dao.AppMapper;
import work.metanet.util.redis.RedisHelper;
import work.metanet.utils.CosUtil;
import work.metanet.utils.LxKeyUtil;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;

@DubboService
public class AppService implements IAppService{
	
	@Autowired
	private AppMapper appMapper;
	@Autowired
	private CosUtil cosUtil;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private RedisHelper redisHelper;

	@Override
	public void clearAppUpgradeCache(String packageName) throws Exception{
		redisHelper.keys(ConstCacheKey.UPGRADE.cacheKey(packageName,"*")).forEach(key->{
			stringRedisTemplate.delete(key);
		});
	}
	
	@Override
	public List<AppVo> appVoList(String channelId) throws Exception{
		return appMapper.appVoList(channelId);
	}
	
	@Override
	public AppVo getAppByPackageName(String packageName) throws Exception{
		App app = appMapper.getApp(MapUtil.builder("packageName", packageName).build());
		if(app==null)throw DaasException.of().setMsg("产品信息错误");
		AppVo appVo = new AppVo();
		BeanUtil.copyProperties(app, appVo);
		return appVo;
	}
	
	@Override
	public AppVo getAppByAppId(String appId) throws Exception {
		App app = appMapper.selectOne(new App().setAppId(appId).setStatus(true));
		if(app==null)throw DaasException.of().setMsg("产品信息错误");
		AppVo appVo = new AppVo();
		BeanUtil.copyProperties(app, appVo);
		return appVo;
	}
	
	/**
	 * @Description: 产品列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespPaging<RespAppList> appList(ReqAppList req) throws Exception {
		RespPaging<RespAppList> respPaging = new RespPaging<RespAppList>();
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<RespAppList> list = appMapper.appList(BeanUtil.beanToMap(req));
		BeanUtil.copyProperties(new PageInfo<RespAppList>(list), respPaging);
		for (RespAppList resp : respPaging.getList()) {
			resp.setAppTypeName(EnumUtil.likeValueOf(ConstAppType.class, resp.getAppType()).getText());
			resp.setAuthTypeName(EnumUtil.likeValueOf(ConstDeviceAuthType.class, resp.getAuthType()).getText());
		}
		return respPaging;
	}
	
	/**
	 * @Description: 产品信息
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespAppInfo appInfo(ReqAppInfo req) throws Exception {
		RespAppInfo resp = appMapper.appInfo(BeanUtil.beanToMap(req));
		if(resp!=null) {
			resp.setAppTypeName(EnumUtil.likeValueOf(ConstAppType.class, resp.getAppType()).getText());
			resp.setAuthTypeName(EnumUtil.likeValueOf(ConstDeviceAuthType.class, resp.getAuthType()).getText());
		}
		return resp;
	}
	
	/**
	 * @Description: 保存产品
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Transactional
	@Override
	public void saveApp(ReqSaveApp req) throws Exception {
		App app = new App();
		BeanUtil.copyProperties(req, app);
		
		App dbApp = appMapper.getApp(MapUtil.builder("packageName",req.getPackageName()).build());
		
		if(StringUtils.isNotBlank(app.getAppId())) {
			//修改操作
			if(!BeanUtil.isEmpty(dbApp) && !dbApp.getAppId().equals(app.getAppId()))
				throw DaasException.of().setMsg("包名已存在");
			if(BeanUtil.isEmpty(dbApp) || dbApp.getAppId().equals(app.getAppId())) {
				app.setAppKey(null);//不要更新
				app.setAppSecret(null);//不要更新
				appMapper.updateByPrimaryKeySelective(app);
			}else {
				throw DaasException.of().setResult(ResultMessage.FAILURE.result());
			}
		}else {
			//新增操作
			if(!BeanUtil.isEmpty(dbApp))
				throw DaasException.of().setMsg("包名已存在");
			app.setAppId(IdUtil.fastSimpleUUID());
			app.setAppKey(LxKeyUtil.appKey());
			app.setAppSecret(LxKeyUtil.appSecret());
			appMapper.insertSelective(app);
			stringRedisTemplate.opsForHash().put(ConstCacheKey.APP_SECRET.cacheKey(), app.getAppId(), JSONUtil.toJsonStr(new AppKeyVo().setAppKey(app.getAppKey()).setAppSecret(app.getAppSecret())));
		}
		
		//清空此产品升级缓存
		clearAppUpgradeCache(app.getPackageName());
	}
	
	/**
	 * @Description: 删除产品
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public void removeApp(List<ReqRemoveApp> req) throws Exception {
		appMapper.removeApp(req);
		for (ReqRemoveApp reqRemoveApp : req) {
			stringRedisTemplate.opsForHash().delete(ConstCacheKey.APP_SECRET.cacheKey(), reqRemoveApp.getAppId());
			App app = appMapper.selectByPrimaryKey(reqRemoveApp.getAppId());
			clearAppUpgradeCache(app.getPackageName());
		}
	}
	
	@Override
	public RespUpgrade upgrade(ReqUpgrade requestParam, String versionCode, String packageName) throws Exception {
		RespUpgrade resp = appMapper.upgrade(requestParam.getDeviceId(),null,null,versionCode, packageName);
		if(BeanUtil.isEmpty(resp))
			throw DaasException.of().setMsg("暂无升级");
		
		if(resp.getUrlType().equals(ConstUrlType._0.getVal())) 
			resp.setUrl(cosUtil.getAccessUrl(resp.getUrl()));
		
		return resp;
	}
	
	
	/**
	 * @Description: 产品升级
	 * @Author wanbo
	 * @DateTime 2019/12/04
	 */
	@Override
	public RespUpgrade upgrade_v2(ReqUpgrade requestParam, String versionCode, String packageName) throws Exception {
		RespUpgrade resp = null;
		//key存在说明之前查询过数据库一次
		if(stringRedisTemplate.hasKey(ConstCacheKey.UPGRADE.cacheKey(packageName,versionCode,requestParam.getBrandName(),requestParam.getModelName()))) {
			resp = JSONUtil.toBean(stringRedisTemplate.opsForValue().get(ConstCacheKey.UPGRADE.cacheKey(packageName,versionCode,requestParam.getBrandName(),requestParam.getModelName())), RespUpgrade.class);
		}else {
			resp = appMapper.upgrade(null,requestParam.getBrandName(),requestParam.getModelName(), versionCode, packageName);
			stringRedisTemplate.opsForValue().set(ConstCacheKey.UPGRADE.cacheKey(packageName,versionCode,requestParam.getBrandName(),requestParam.getModelName()), BeanUtil.isEmpty(resp)?"{}":JSONUtil.toJsonStr(resp),Duration.ofSeconds(ConstCacheKey.UPGRADE.getExpire()));
		}
		if(resp==null || StrUtil.isBlank(resp.getUpgradePlanId())) throw DaasException.of().setResult(ResultMessage.FAILURE_TIP_NOT_UPGRADE.result());
		
		if(resp.getUrlType().equals(ConstUrlType._0.getVal())) 
			resp.setUrl(cosUtil.getAccessUrl(resp.getUrl()));
		
		return resp;
	}
	
	/**
	 * @Description: 启用激活码激活
	 * @Author wanbo
	 * @DateTime 2020/03/09
	 */
	@Override
	public void enableSn(ReqEnableSn req) throws Exception {
		App app = appMapper.selectByPrimaryKey(req.getAppId());
		if(app!=null) {
			app.setEnableSn(req.getEnableSn());
			appMapper.updateByPrimaryKeySelective(app);
		}else {
			throw DaasException.of().setMsg("操作失败");
		}
	}
	
	@Override
	public AppKeyVo appKey(String appId) {
		String cacheKey = ConstCacheKey.APP_SECRET.cacheKey();
		AppKeyVo appKeyVo = null;
		appKeyVo = BeanUtil.toBean(stringRedisTemplate.opsForHash().get(cacheKey,appId), AppKeyVo.class);
		
		if(StrUtil.isBlank(appKeyVo.getAppKey()) || StrUtil.isBlank(appKeyVo.getAppSecret())) {
			App app = appMapper.selectByPrimaryKey(appId);
			appKeyVo = new AppKeyVo().setAppKey(app.getAppKey()).setAppSecret(app.getAppSecret());
		}
		stringRedisTemplate.opsForHash().put(cacheKey, appId, JSONUtil.toJsonStr(appKeyVo));
		return appKeyVo;
	}
	
	
}
