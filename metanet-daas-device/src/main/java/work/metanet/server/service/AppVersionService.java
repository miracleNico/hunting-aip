package work.metanet.server.service;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import work.metanet.api.app.IAppService;
import work.metanet.api.version.IAppVersionService;
import work.metanet.api.version.protocol.ReqAppVersionInfo;
import work.metanet.api.version.protocol.ReqAppVersionInfo.RespAppVersionInfo;
import work.metanet.api.version.protocol.ReqAppVersionList;
import work.metanet.api.version.protocol.ReqAppVersionList.RespAppVersionList;
import work.metanet.api.version.protocol.ReqRemoveAppVersion;
import work.metanet.api.version.protocol.ReqSaveAppVersion;
import work.metanet.api.version.protocol.ReqSaveAppVersion.RespSaveAppVersion;
import work.metanet.api.version.vo.AppVersionVo;
import work.metanet.base.RespPaging;
import work.metanet.base.ResultMessage;
import work.metanet.constant.ConstUrlType;
import work.metanet.constant.ConstVersionType;
import work.metanet.exception.DaasException;
import work.metanet.model.App;
import work.metanet.model.AppVersion;
import work.metanet.server.dao.AppMapper;
import work.metanet.server.dao.AppVersionMapper;
import work.metanet.utils.CosUtil;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;

@DubboService
public class AppVersionService implements IAppVersionService{

	@Autowired
	private AppVersionMapper appVersionMapper;
	@Autowired
	private CosUtil cosUtil;
	@Autowired
	private AppMapper appMapper;
	@Autowired
	private IAppService appService;
	
	@Override
	public AppVersionVo getAppVersion(String packageName, String versionCode)throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("packageName", packageName);
		map.put("versionCode", versionCode);
		AppVersion appVersion = appVersionMapper.getAppVersion(map);
		if(BeanUtil.isEmpty(appVersion))
			throw DaasException.of().setMsg("版本信息错误");
		AppVersionVo appVersionVo = new AppVersionVo();
		BeanUtil.copyProperties(appVersion, appVersionVo);
		return appVersionVo;
	}
	
	/**
	 * @Description: 版本列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespPaging<RespAppVersionList> appVersionList(ReqAppVersionList req) throws Exception {
		RespPaging<RespAppVersionList> respPaging = new RespPaging<RespAppVersionList>();
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<RespAppVersionList> list = appVersionMapper.appVersionList(BeanUtil.beanToMap(req));
		BeanUtil.copyProperties(new PageInfo<RespAppVersionList>(list), respPaging);
		for (RespAppVersionList resp : respPaging.getList()) {
			if(resp.getUrlType().equals(ConstUrlType._0.getVal()))
				resp.setUrl(cosUtil.getAccessUrl(resp.getUrl()));
			if(resp.getFileSize()!=null)
				resp.setFileSize(resp.getFileSize().divide(BigDecimal.valueOf(1024*1024)).setScale(2, BigDecimal.ROUND_HALF_UP));
			resp.setVersionTypeName(ConstVersionType.getText(resp.getVersionType()));
		}
		return respPaging;
	}

	/**
	 * @Description: 版本信息
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespAppVersionInfo appVersionInfo(ReqAppVersionInfo req) throws Exception {
		RespAppVersionInfo resp = appVersionMapper.appVersionInfo(BeanUtil.beanToMap(req));
		if(resp!=null) {
			if(resp.getUrlType().equals(ConstUrlType._0.getVal()))
				resp.setUrl(cosUtil.getAccessUrl(resp.getUrl()));
			resp.setVersionTypeName(ConstVersionType.getText(resp.getVersionType()));
		}
		return resp;
	}

	/**
	 * @Description: 保存版本
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespSaveAppVersion saveAppVersion(ReqSaveAppVersion req) throws Exception {
		AppVersion appVersion = new AppVersion();
		BeanUtil.copyProperties(req, appVersion);
		
		appVersion.setUrlType(appVersion.getUrl().contains("myqcloud.com")?ConstUrlType._0.getVal():ConstUrlType._1.getVal());
		if(appVersion.getUrlType().equals(ConstUrlType._0.getVal())) {
			appVersion.setUrl(cosUtil.filterUrlDomain(appVersion.getUrl()));			
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("versionCode", req.getVersionCode());
		map.put("appId", req.getAppId());
		AppVersion dbAppVersion = appVersionMapper.getAppVersion(map);
		
		if(StringUtils.isNotBlank(appVersion.getVersionId())) {
			//修改操作
			if(!BeanUtil.isEmpty(dbAppVersion) && !dbAppVersion.getVersionId().equals(appVersion.getVersionId()))
				throw DaasException.of().setMsg("版本号已存在");
			if(BeanUtil.isEmpty(dbAppVersion) || dbAppVersion.getVersionId().equals(appVersion.getVersionId())) {
				appVersionMapper.updateByPrimaryKeySelective(appVersion);
			}else {
				throw DaasException.of().setResult(ResultMessage.FAILURE.result());
			}
		}else {
			//新增操作
			if(!BeanUtil.isEmpty(dbAppVersion)) throw DaasException.of().setMsg("版本号已存在");
			
			appVersion.setVersionId(IdUtil.fastSimpleUUID());
			appVersionMapper.insertSelective(appVersion);
		}
		
		App app = appMapper.selectByPrimaryKey(appVersion.getAppId());
		appService.clearAppUpgradeCache(app.getPackageName());
		
		RespSaveAppVersion resp = new RespSaveAppVersion();
		BeanUtil.copyProperties(appVersion, resp);
		return resp;
	}

	/**
	 * @Description: 删除版本
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public void removeAppVersion(List<ReqRemoveAppVersion> req) throws Exception {
		for (ReqRemoveAppVersion reqRemoveAppVersion : req) {
			AppVersion appVersion = appVersionMapper.selectByPrimaryKey(reqRemoveAppVersion.getVersionId());
			App app = appMapper.selectByPrimaryKey(appVersion.getAppId());
			appService.clearAppUpgradeCache(app.getPackageName());
		}
		appVersionMapper.removeAppVersion(req);
	}
	
	
	
	
	
	
	
	
}
