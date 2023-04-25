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
import work.metanet.api.sn.ISerialNumberService;
import work.metanet.api.sn.protocol.ReqSerialNumberList;
import work.metanet.api.sn.protocol.ReqSerialNumberList.RespSerialNumberList;
import work.metanet.base.BaseController;
import work.metanet.base.RespPaging;
import work.metanet.base.Result;
import work.metanet.base.ResultMessage;

@ApiModule(module = Module.SN)
@Controller
@RequestMapping("sn")
public class SerialNumberController extends BaseController{
	
	@DubboReference ISerialNumberService serialNumberService;
	
	@RequestMapping("listPage")
	public String listPage() {
		return "page/sn/list";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("serialNumberList")
	public Result<RespPaging<RespSerialNumberList>> serialNumberList(@RequestBody ReqSerialNumberList req) throws Exception {
		req.setChannelId(getChannelId());
		return ResultMessage.SUCCESS.result(serialNumberService.serialNumberList(req));
	}
	
	
}
