package work.metanet.server.service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import work.metanet.api.app.IAppService;
import work.metanet.api.app.vo.AppVo;
import work.metanet.api.storeApp.IStoreAppService;
import work.metanet.api.storeApp.protocol.ReqRemoveStoreApp;
import work.metanet.api.storeApp.protocol.ReqSaveStoreApp;
import work.metanet.api.storeApp.protocol.ReqSearchStoreApp;
import work.metanet.api.storeApp.protocol.ReqSearchStoreApp.RespSearchStoreApp;
import work.metanet.api.storeApp.protocol.ReqStoreAppInfo;
import work.metanet.api.storeApp.protocol.ReqStoreAppInfo.RespStoreAppInfo;
import work.metanet.api.storeApp.protocol.ReqStoreAppList;
import work.metanet.api.storeApp.protocol.ReqStoreAppList.RespStoreAppList;
import work.metanet.api.storeApp.protocol.ReqStoreAppScopeInfo.RespStoreAppScopeInfo;
import work.metanet.base.RespPaging;
import work.metanet.base.ResultMessage;
import work.metanet.constant.ConstCacheKey;
import work.metanet.constant.ConstStoreAppScope;
import work.metanet.constant.ConstUpgradeScope;
import work.metanet.exception.DaasException;
import work.metanet.model.StoreApp;
import work.metanet.model.StoreAppScope;
import work.metanet.server.dao.StoreAppMapper;
import work.metanet.util.redis.RedisHelper;
import work.metanet.utils.CosUtil;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

@DubboService
public class StoreAppService implements IStoreAppService{

	@Autowired
	private StoreAppMapper storeAppMapper;
	@Autowired
	private CosUtil cosUtil;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private RedisHelper redisHelper;
	@DubboReference
	private IAppService appService;
	
	@Override
	public RespPaging<RespSearchStoreApp> searchStoreApp(ReqSearchStoreApp req) throws Exception {
		RespPaging<RespSearchStoreApp> respPaging = new RespPaging<RespSearchStoreApp>();
		AppVo appVo = appService.getAppByPackageName(req.getPackageName());
		req.setChannelId(appVo.getChannelId());
		String cacheKey = ConstCacheKey.STORE_APP.cacheKey(String.valueOf(req.hashCode()));
		Object obj = stringRedisTemplate.opsForValue().get(cacheKey);
		respPaging = JSONUtil.toBean(new JSONObject(obj),RespPaging.class,true);
		if(ObjectUtil.isEmpty(obj)) {
			PageHelper.startPage(req.getPageNum(), req.getPageSize());
			List<RespSearchStoreApp> list = storeAppMapper.searchStoreApp(req);
			for (RespSearchStoreApp resp : list) {
				resp.setIcon(cosUtil.getAccessUrl(resp.getIcon()));
				resp.setUrl(cosUtil.getAccessUrl(resp.getUrl()));
				if(resp.getFileSize()!=null)resp.setFileSize(resp.getFileSize().divide(BigDecimal.valueOf(1024*1024)).setScale(2, BigDecimal.ROUND_HALF_UP));
			}
			BeanUtil.copyProperties(new PageInfo<RespSearchStoreApp>(list), respPaging);
			stringRedisTemplate.opsForValue().set(cacheKey, JSONUtil.toJsonStr(respPaging), ConstCacheKey.STORE_APP.getExpire(),TimeUnit.SECONDS);
		}
		return respPaging;
	}
	
	/**
	 * @Description: 应用商店列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespPaging<RespStoreAppList> storeAppList(ReqStoreAppList req) throws Exception {
		RespPaging<RespStoreAppList> respPaging = new RespPaging<RespStoreAppList>();
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<RespStoreAppList> list = storeAppMapper.storeAppList(BeanUtil.beanToMap(req));
		for (RespStoreAppList resp : list) {
			resp.setAppScopeName(EnumUtil.fromStringQuietly(ConstStoreAppScope.class, StrUtil.concat(true, "_",resp.getAppScope())).getText());
			resp.setIcon(cosUtil.getAccessUrl(resp.getIcon()));
			resp.setUrl(cosUtil.getAccessUrl(resp.getUrl()));
			if(resp.getFileSize()!=null)resp.setFileSize(resp.getFileSize().divide(BigDecimal.valueOf(1024*1024)).setScale(2, BigDecimal.ROUND_HALF_UP));
		}
		BeanUtil.copyProperties(new PageInfo<RespStoreAppList>(list), respPaging);
		return respPaging;
	}

	/**
	 * @Description: 应用商店信息
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespStoreAppInfo storeAppInfo(ReqStoreAppInfo req) throws Exception {
		RespStoreAppInfo resp = storeAppMapper.storeAppInfo(BeanUtil.beanToMap(req));
		resp.setUrl(cosUtil.getAccessUrl(resp.getUrl()));
		resp.setIcon(cosUtil.getAccessUrl(resp.getIcon()));
		if(StrUtil.isNotBlank(resp.getImages())) {
			List<String> imgs = new ArrayList<String>();
			for(String img : StrUtil.split(resp.getImages(), ',')) {
				imgs.add(cosUtil.getAccessUrl(img));
			}
			resp.setImages(StrUtil.join(",", imgs));
		}
		if(resp.getAppScope().equals(ConstUpgradeScope.SPECIFIED.getVal())) {
			List<RespStoreAppScopeInfo> storeAppScopeInfos = storeAppMapper.storeAppScopeInfoList(resp.getStoreAppId());
			resp.setStoreAppScopeInfoList(storeAppScopeInfos);
		}
		return resp;
	}

	/**
	 * @Description: 保存应用商店
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	@Transactional
	public void saveStoreApp(ReqSaveStoreApp req) throws Exception {
		if(ConstUpgradeScope.SPECIFIED.getVal().equals(req.getAppScope()) && CollectionUtil.isEmpty(req.getStoreAppScopeInfoList()))
			throw DaasException.of().setMsg("指定范围不能为空，请选择！");
		
		StoreApp storeApp = new StoreApp();
		BeanUtil.copyProperties(req, storeApp);
		
		storeApp.setUrl(cosUtil.filterUrlDomain(storeApp.getUrl()));
		storeApp.setIcon(cosUtil.filterUrlDomain(storeApp.getIcon()));
		if(StrUtil.isNotBlank(storeApp.getImages())) {
			List<String> imgs = new ArrayList<String>();
			for(String img : StrUtil.split(storeApp.getImages(), ',')) {
				imgs.add(cosUtil.filterUrlDomain(img));
			}
			storeApp.setImages(StrUtil.join(",", imgs));
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("packageName", req.getPackageName());
		StoreApp dbStoreApp = storeAppMapper.getStoreApp(map);
		
		if(StringUtils.isNotBlank(storeApp.getStoreAppId())) {
			//修改操作
			if(!BeanUtil.isEmpty(dbStoreApp) && !dbStoreApp.getStoreAppId().equals(storeApp.getStoreAppId()))
				throw DaasException.of().setMsg("包名已存在");
			if(BeanUtil.isEmpty(dbStoreApp) || dbStoreApp.getStoreAppId().equals(storeApp.getStoreAppId())) {
				storeAppMapper.updateByPrimaryKeySelective(storeApp);
				resetStoreAppScope(req.getStoreAppScopeInfoList(), storeApp.getStoreAppId());
			}else {
				throw DaasException.of().setResult(ResultMessage.FAILURE.result());
			}
		}else {
			//新增操作
			if(!BeanUtil.isEmpty(dbStoreApp)) throw DaasException.of().setMsg("包名已存在");
			String storeAppId = IdUtil.fastSimpleUUID();
			storeApp.setStoreAppId(storeAppId);
			storeAppMapper.insertSelective(storeApp);
			resetStoreAppScope(req.getStoreAppScopeInfoList(), storeAppId);
		}
		
		redisHelper.keys(ConstCacheKey.STORE_APP.cacheKey("*")).forEach(key->{
			stringRedisTemplate.delete(key);
		});
	}
	
	private void resetStoreAppScope(List<RespStoreAppScopeInfo> list,String storeAppId) {
		if(CollectionUtil.isNotEmpty(list)) {
			storeAppMapper.removeStoreAppScope(storeAppId);
			List<StoreAppScope> storeAppScopes = JSONUtil.toList(new JSONArray(list), StoreAppScope.class);
			for (StoreAppScope storeAppScope : storeAppScopes) {
				storeAppScope.setStoreAppScopeId(IdUtil.fastSimpleUUID());
				storeAppScope.setStoreAppId(storeAppId);
			}
			storeAppMapper.addStoreAppScope(storeAppScopes);
		}
	}
	
	/**
	 * @Description: 删除应用商店
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public void removeStoreApp(List<ReqRemoveStoreApp> req) throws Exception {
		storeAppMapper.removeStoreApp(req);
		redisHelper.keys(ConstCacheKey.STORE_APP.cacheKey("*")).forEach(key->{
			stringRedisTemplate.delete(key);
		});
	}
	
	
	
	
	
}
