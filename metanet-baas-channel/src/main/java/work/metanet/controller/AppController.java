package work.metanet.controller;

import javax.servlet.http.HttpServletRequest;

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
		req.setChannelId(getChannelId());
		return ResultMessage.SUCCESS.result(appService.appList(req));
	}
	
	@ApiPermission(AUTH.OPEN)
	@ResponseBody
	@RequestMapping("appSelected")
	public Result<RespPaging<RespAppList>> appSelected(@RequestBody ReqAppList req) throws Exception {
		req.setChannelId(getChannelId());
		return ResultMessage.SUCCESS.result(appService.appList(req));
	}
	
	
}
