package work.metanet.controller;

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
import work.metanet.aop.ApiPermission;
import work.metanet.aop.ApiPermission.AUTH;
import work.metanet.api.version.IAppVersionService;
import work.metanet.api.version.protocol.ReqAppVersionInfo;
import work.metanet.api.version.protocol.ReqAppVersionInfo.RespAppVersionInfo;
import work.metanet.api.version.protocol.ReqAppVersionList;
import work.metanet.api.version.protocol.ReqAppVersionList.RespAppVersionList;
import work.metanet.api.version.protocol.ReqValidAppVersionList;
import work.metanet.base.BaseController;
import work.metanet.base.RespPaging;
import work.metanet.base.Result;
import work.metanet.base.ResultMessage;

@ApiModule(module = Module.VERSION)
@Controller
@RequestMapping("version")
public class AppVersionController extends BaseController{
	
	@DubboReference IAppVersionService appVersionService;
	
	@RequestMapping("listPage")
	public String listPage() {
		return "page/version/list";
	}
	
	@ApiOperLog(action = ACTION.INFO)
	@RequestMapping("infoPage")
	public String infoPage(HttpServletRequest request,String versionId) throws Exception {
		RespAppVersionList info = new RespAppVersionList();
		if(StringUtils.isNotBlank(versionId)) {
			info = appVersionService.appVersionInfo(new ReqAppVersionInfo().setVersionId(versionId));
		}
		request.setAttribute("info", info);
		return "page/version/info";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("versionList")
	public Result<RespPaging<RespAppVersionList>> versionList(@Valid @RequestBody ReqAppVersionList req) throws Exception {
		req.setChannelId(getChannelId());
		return ResultMessage.SUCCESS.result(appVersionService.appVersionList(req));
	}
	
	@ApiPermission(AUTH.OPEN)
	@ResponseBody
	@RequestMapping("appVersionSelected")
	public Result<RespPaging<RespAppVersionList>> appVersionSelected(@Valid @RequestBody ReqValidAppVersionList req) throws Exception {
		if(StringUtils.isBlank(req.getApp()))
			return ResultMessage.SUCCESS.result(new RespPaging<RespAppVersionList>());
		return ResultMessage.SUCCESS.result(appVersionService.appVersionList(req));
	}
	
	@ResponseBody
	@RequestMapping("appVersionInfo")
	public Result<RespAppVersionInfo> appVersionInfo(@RequestBody ReqAppVersionInfo req) throws Exception {
		return ResultMessage.SUCCESS.result(appVersionService.appVersionInfo(req));
	}
	
	
	
}
