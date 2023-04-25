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
import work.metanet.api.openDevice.IOpenDeviceService;
import work.metanet.api.openDevice.protocol.ReqOpenDeviceEnable;
import work.metanet.api.openDevice.protocol.ReqOpenDeviceList;
import work.metanet.api.openDevice.protocol.ReqOpenDeviceList.RespOpenDeviceList;
import work.metanet.api.openDevice.protocol.ReqOpenDeviceRemove;
import work.metanet.base.BaseController;
import work.metanet.base.RespPaging;
import work.metanet.base.Result;
import work.metanet.base.ResultMessage;
import work.metanet.model.OpenDevice;
import work.metanet.utils.ValidList;

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
		return ResultMessage.SUCCESS.result(openDeviceService.deviceList(req));
	}
	
	@RequestMapping("editPage")
	public String editPage(HttpServletRequest request,String openDeviceId) throws Exception {
		OpenDevice info = new OpenDevice();
		if(StringUtils.isNotBlank(openDeviceId)) {
			info = openDeviceService.info(openDeviceId);
		}
		request.setAttribute("info", info);
		return "page/openDevice/edit";
	}
	
	@ApiOperLog(action = ACTION.DELETE)
	@ResponseBody
	@RequestMapping("removeDevice")
	public Result<?> removeDevice(@Valid @RequestBody ValidList<ReqOpenDeviceRemove> req) throws Exception {
		openDeviceService.removeDevice(req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperLog(action = ACTION.UPDATE)
	@ResponseBody
	@RequestMapping("enable")
	public Result<?> enable(@Valid @RequestBody ReqOpenDeviceEnable req) throws Exception {
		openDeviceService.enable(req);
		return ResultMessage.SUCCESS.result();
	}
	
	
}
