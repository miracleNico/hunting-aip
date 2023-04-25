package work.metanet.server.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import work.metanet.api.versionModule.IAppVersionModuleService;
import work.metanet.api.versionModule.protocol.ReqAppVersionModuleInfo;
import work.metanet.api.versionModule.protocol.ReqAppVersionModuleInfo.RespAppVersionModuleInfo;
import work.metanet.api.versionModule.protocol.ReqAppVersionModuleList;
import work.metanet.api.versionModule.protocol.ReqAppVersionModuleList.RespAppVersionModuleList;
import work.metanet.api.versionModule.protocol.ReqRemoveAppVersionModule;
import work.metanet.api.versionModule.protocol.ReqSaveAppVersionModule;
import work.metanet.api.versionModule.protocol.ReqUpdAppVersionModuleParent;
import work.metanet.api.versionModule.protocol.ReqViewAppVersionModule.RespViewAppVersionModule;
import work.metanet.base.ResultMessage;
import work.metanet.exception.DaasException;
import work.metanet.model.AppVersionModule;
import work.metanet.server.dao.AppVersionModuleMapper;
import work.metanet.utils.CosUtil;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;

@DubboService
public class AppVersionModuleService implements IAppVersionModuleService{

	@Autowired
	private AppVersionModuleMapper appVersionModuleMapper;
	@Autowired
	private CosUtil cosUtil;
	
	/**
	 * @Description: 展示产品模块
	 * @Author wanbo
	 * @DateTime 2020/03/06
	 */
	@Override
	public RespViewAppVersionModule viewAppVersionModule(String packageName, String versionCode) throws Exception {
		RespViewAppVersionModule resp = appVersionModuleMapper.viewAppVersionModule(packageName, versionCode);
		if(resp!=null) {
			if(StringUtils.isNotBlank(resp.getModuleIcon())) {
				resp.setModuleIcon(cosUtil.getAccessUrl(resp.getModuleIcon()));
			}
			if(StringUtils.isNotBlank(resp.getBusinessAppsUrl())) {
				List<String> businessAppsUrls = StrUtil.split(resp.getBusinessAppsUrl(), ',');
				for(String appUrl : businessAppsUrls) {
					appUrl = cosUtil.getAccessUrl(appUrl);
				}
				resp.setBusinessAppsUrl(StrUtil.join(",", businessAppsUrls));
			}
		}
		return resp;
	}
	
	/**
	 * @Description: 版本模块列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public List<RespAppVersionModuleList> appVersionModuleList(ReqAppVersionModuleList req) throws Exception {
		List<RespAppVersionModuleList> list = appVersionModuleMapper.appVersionModuleList(BeanUtil.beanToMap(req));
		return list;
	}

	/**
	 * @Description: 版本模块信息
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespAppVersionModuleInfo appVersionModuleInfo(ReqAppVersionModuleInfo req) throws Exception {
		RespAppVersionModuleInfo resp = appVersionModuleMapper.appVersionModuleInfo(BeanUtil.beanToMap(req));
		if(StringUtils.isNoneBlank(resp.getModuleIcon())) {
			resp.setModuleIcon(cosUtil.getAccessUrl(resp.getModuleIcon()));			
		}
		return resp;
	}

	/**
	 * @Description: 保存版本模块
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public void saveAppVersionModule(ReqSaveAppVersionModule req) throws Exception {
		AppVersionModule versionModule = new AppVersionModule();
		BeanUtil.copyProperties(req, versionModule);
		if(StringUtils.isNoneBlank(versionModule.getModuleIcon())) {
			versionModule.setModuleIcon(cosUtil.filterUrlDomain(versionModule.getModuleIcon()));			
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("versionId", req.getVersionId());
		map.put("moduleName", req.getModuleName());
		AppVersionModule dbAppVersionModule = appVersionModuleMapper.getAppVersionModule(map);
		
		if(StringUtils.isNotBlank(versionModule.getVersionModuleId())) {
			//修改操作
			if(dbAppVersionModule!=null && !dbAppVersionModule.getVersionModuleId().equals(versionModule.getVersionModuleId()))
				throw DaasException.of().setMsg("名称已存在");
			if(dbAppVersionModule==null || dbAppVersionModule.getVersionModuleId().equals(versionModule.getVersionModuleId())) {
				appVersionModuleMapper.updateByPrimaryKeySelective(versionModule);
			}else {
				throw DaasException.of().setResult(ResultMessage.FAILURE.result());
			}
		}else {
			//新增操作
			if(!BeanUtil.isEmpty(dbAppVersionModule))
				throw DaasException.of().setMsg("名称已存在");
			versionModule.setVersionModuleId(IdUtil.fastSimpleUUID());
			appVersionModuleMapper.insertSelective(versionModule);
		}
		
	}

	/**
	 * @Description: 删除版本模块
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public void removeAppVersionModule(List<ReqRemoveAppVersionModule> req) throws Exception {
		appVersionModuleMapper.removeAppVersionModule(req);
	}
	
	
	/**
	 * @Description: 修改父级节点
	 * @Author wanbo
	 * @DateTime 2020/02/27
	 */
	@Override
	public void updAppVersionModuleParent(List<ReqUpdAppVersionModuleParent> req) throws Exception {
		for (ReqUpdAppVersionModuleParent o : req) {
			if(StringUtils.isEmpty(o.getParentId())) {
				o.setParentId("");
			}
			appVersionModuleMapper.updAppVersionModuleParent(o.getVersionModuleId(), o.getParentId());			
		}
	}
	
	
	
	
	
}
