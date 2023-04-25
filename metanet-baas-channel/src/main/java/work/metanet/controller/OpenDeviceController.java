package work.metanet.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import work.metanet.aop.ApiModule;
import work.metanet.aop.ApiModule.Module;
import work.metanet.aop.ApiOperLog;
import work.metanet.aop.ApiOperLog.ACTION;
import work.metanet.api.openDevice.IOpenDeviceService;
import work.metanet.api.openDevice.protocol.ReqOpenDeviceList;
import work.metanet.api.openDevice.protocol.ReqOpenDeviceList.RespOpenDeviceList;
import work.metanet.base.BaseController;
import work.metanet.base.RespPaging;
import work.metanet.base.Result;
import work.metanet.base.ResultMessage;

@ApiModule(module = Module.OPEN_DEVICE)
@Controller
@RequestMapping("openDevice")
public class OpenDeviceController extends BaseController{
	
	@DubboReference
	private IOpenDeviceService openDeviceService;
	
	@RequestMapping("listPage")
	public String listPage() {
		return "page/openDevice/list";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("deviceList")
	public Result<RespPaging<RespOpenDeviceList>> deviceList(@RequestBody ReqOpenDeviceList req) throws Exception {
		req.setChannelId(getChannelId());
		return ResultMessage.SUCCESS.result(openDeviceService.deviceList(req));
	}
	
}
