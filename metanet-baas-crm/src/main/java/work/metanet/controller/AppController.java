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
import work.metanet.aop.ApiPermission;
import work.metanet.aop.ApiPermission.AUTH;
import work.metanet.api.app.IAppService;
import work.metanet.api.app.protocol.ReqAppInfo;
import work.metanet.api.app.protocol.ReqAppInfo.RespAppInfo;
import work.metanet.api.app.protocol.ReqAppList;
import work.metanet.api.app.protocol.ReqAppList.RespAppList;
import work.metanet.api.app.protocol.ReqRemoveApp;
import work.metanet.api.app.protocol.ReqSaveApp;
import work.metanet.base.BaseController;
import work.metanet.base.RespPaging;
import work.metanet.base.Result;
import work.metanet.base.ResultMessage;

@ApiModule(module = Module.APP)
@Controller
@RequestMapping("app")
public class AppController extends BaseController{
	
	@DubboReference IAppService appService;
	
	@RequestMapping("listPage")
	public String listPage() {
		return "page/app/list";
	}
	
	@RequestMapping("editPage")
	public String editPage(HttpServletRequest request,String appId) throws Exception {
		RespAppInfo info = new RespAppInfo();
		if(StringUtils.isNotBlank(appId)) {
			info = appService.appInfo(new ReqAppInfo().setAppId(appId));
		}
		request.setAttribute("info", info);
		return "page/app/edit";
	}
	
	@ApiOperLog(action = ACTION.INFO)
	@RequestMapping("infoPage")
	public String infoPage(HttpServletRequest request,String appId) throws Exception {
		RespAppInfo info = new RespAppInfo();
		if(StringUtils.isNotBlank(appId)) {
			info = appService.appInfo(new ReqAppInfo().setAppId(appId));
		}
		request.setAttribute("info", info);
		return "page/app/info";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("appList")
	public Result<RespPaging<RespAppList>> appList(@RequestBody ReqAppList req) throws Exception {
		return ResultMessage.SUCCESS.result(appService.appList(req));
	}
	
	@ApiPermission(AUTH.OPEN)
	@ResponseBody
	@RequestMapping("appSelected")
	public Result<RespPaging<RespAppList>> appSelected(@RequestBody ReqAppList req) throws Exception {
		return ResultMessage.SUCCESS.result(appService.appList(req));
	}
	
	@ApiPermission(AUTH.OPEN)
	@ResponseBody
	@RequestMapping("channelAppSelected")
	public Result<RespPaging<RespAppList>> channelAppSelected(@RequestBody ReqAppList req) throws Exception {
		if(StringUtils.isBlank(req.getChannelId())) 
			return ResultMessage.SUCCESS.result(new RespPaging<RespAppList>());
		return ResultMessage.SUCCESS.result(appService.appList(req));
	}
	
	@ApiOperLog(action = ACTION.SAVE)
	@ResponseBody
	@RequestMapping("saveApp")
	public Result<?> saveApp(@RequestBody ReqSaveApp req) throws Exception {
		appService.saveApp(req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperLog(action = ACTION.DELETE)
	@ResponseBody
	@RequestMapping("removeApp")
	public Result<?> removeApp(@Valid @RequestBody List<ReqRemoveApp> req) throws Exception {
		appService.removeApp(req);
		return ResultMessage.SUCCESS.result();
	}
	
	/**
	@ApiOperLog(action = ACTION.UPDATE)
	@ResponseBody
	@RequestMapping("enableSn")
	public Result<?> enableSn(@Valid @RequestBody ReqEnableSn req) throws Exception {
		appService.enableSn(req);
		return ResultMessage.SUCCESS.result();
	}*/
	
	
}
