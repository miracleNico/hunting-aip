package work.metanet.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import work.metanet.aop.ApiModule;
import work.metanet.aop.ApiModule.Module;
import work.metanet.aop.ApiOperLog;
import work.metanet.aop.ApiOperLog.ACTION;
import work.metanet.api.versionModule.IAppVersionModuleService;
import work.metanet.api.versionModule.protocol.ReqAppVersionModuleInfo;
import work.metanet.api.versionModule.protocol.ReqAppVersionModuleInfo.RespAppVersionModuleInfo;
import work.metanet.api.versionModule.protocol.ReqAppVersionModuleList;
import work.metanet.api.versionModule.protocol.ReqAppVersionModuleList.RespAppVersionModuleList;
import work.metanet.api.versionModule.protocol.ReqRemoveAppVersionModule;
import work.metanet.api.versionModule.protocol.ReqSaveAppVersionModule;
import work.metanet.api.versionModule.protocol.ReqUpdAppVersionModuleParent;
import work.metanet.base.BaseController;
import work.metanet.base.Result;
import work.metanet.base.ResultMessage;

@ApiModule(module = Module.BUSINESS_CONTENT)
@Controller
@RequestMapping("appVersionModule")
public class AppVersionModuleController extends BaseController{
	
	@DubboReference IAppVersionModuleService appVersionModuleService;
	
	@RequestMapping(value = "listPage")
	public String listPage(String versionId,HttpServletRequest request) {
		request.setAttribute("versionId", versionId);
		return "page/versionModule/list";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("appVersionModuleList")
	public Result<List<RespAppVersionModuleList>> appVersionModuleList(@Valid @RequestBody ReqAppVersionModuleList req) throws Exception {
		return ResultMessage.SUCCESS.result(appVersionModuleService.appVersionModuleList(req));
	}
	
	@RequestMapping("editPage")
	public String editPage(String versionModuleId,String versionId,String parentId,HttpServletRequest request) throws Exception {
		RespAppVersionModuleInfo info = new RespAppVersionModuleInfo();
		if(StringUtils.isNotBlank(versionModuleId)) {
			info = appVersionModuleService.appVersionModuleInfo(new ReqAppVersionModuleInfo().setVersionModuleId(versionModuleId));
			if(info==null) 
				info = new RespAppVersionModuleInfo();
		}
		if(StringUtils.isEmpty(info.getVersionId())) {
			info.setVersionId(versionId);
		}
		if(StringUtils.isEmpty(info.getParentId())) {
			info.setParentId(parentId);
		}
		request.setAttribute("info", info);
		return "page/versionModule/edit";
	}
	
	@ApiOperLog(action = ACTION.SAVE)
	@ResponseBody
	@RequestMapping("saveAppVersionModule")
	public Result<?> saveAppVersionModule(@RequestBody ReqSaveAppVersionModule req) throws Exception {
		appVersionModuleService.saveAppVersionModule(req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperLog(action = ACTION.DELETE)
	@ResponseBody
	@RequestMapping("removeAppVersionModule")
	public Result<?> removeAppVersionModule(@Valid @RequestBody List<ReqRemoveAppVersionModule> req) throws Exception {
		appVersionModuleService.removeAppVersionModule(req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperLog(action = ACTION.UPDATE)
	@ResponseBody
	@RequestMapping("updAppVersionModuleParent")
	public Result<?> updAppVersionModuleParent(@Valid @RequestBody List<ReqUpdAppVersionModuleParent> req) throws Exception {
		appVersionModuleService.updAppVersionModuleParent(req);
		return ResultMessage.SUCCESS.result();
	}
	
}
