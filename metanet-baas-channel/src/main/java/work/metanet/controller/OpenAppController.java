package work.metanet.controller;

import java.util.HashMap;
import java.util.List;

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
import work.metanet.api.openApp.IOpenAppService;
import work.metanet.api.openApp.protocol.ReqOpenAppList;
import work.metanet.api.openApp.protocol.ReqOpenAppList.RespOpenAppList;
import work.metanet.base.BaseController;
import work.metanet.base.RespPaging;
import work.metanet.base.Result;
import work.metanet.base.ResultMessage;
import work.metanet.model.OpenApp;

import cn.hutool.core.map.MapUtil;

@ApiModule(module = Module.OPEN_APP)
@Controller
@RequestMapping("openApp")
public class OpenAppController extends BaseController{
	
	@DubboReference
	private IOpenAppService openAppService;
	
	@RequestMapping("listPage")
	public String listPage() {
		return "page/openApp/list";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("appList")
	public Result<RespPaging<RespOpenAppList>> appList(@RequestBody ReqOpenAppList req) throws Exception {
		req.setChannelId(getChannelId());
		return ResultMessage.SUCCESS.result(openAppService.appList(req));
	}
	
	@ApiPermission(AUTH.OPEN)
	@ResponseBody
	@RequestMapping("channelAppSelected")
	public Result<List<OpenApp>> channelAppSelected(@RequestBody OpenApp req) throws Exception {
		String channelId = getChannelId();
		return ResultMessage.SUCCESS.result(openAppService.appSelected(MapUtil.builder(new HashMap<String,Object>()).put("channelId", channelId).build()));
	}
	
}
