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
import work.metanet.api.sn.ISerialNumberService;
import work.metanet.api.sn.protocol.ReqRemoveSerialNumber;
import work.metanet.api.sn.protocol.ReqSaveSerialNumber;
import work.metanet.api.sn.protocol.ReqSerialNumberInfo;
import work.metanet.api.sn.protocol.ReqSerialNumberInfo.RespSerialNumberInfo;
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
	
	@RequestMapping("editPage")
	public String editPage(HttpServletRequest request,String snCode) throws Exception {
		RespSerialNumberInfo info = new RespSerialNumberInfo();
		if(StringUtils.isNotBlank(snCode)) {
			info = serialNumberService.serialNumberInfo(new ReqSerialNumberInfo().setSnCode(snCode));
		}
		request.setAttribute("info", info);
		return "page/sn/edit";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("serialNumberList")
	public Result<RespPaging<RespSerialNumberList>> serialNumberList(@RequestBody ReqSerialNumberList req) throws Exception {
		return ResultMessage.SUCCESS.result(serialNumberService.serialNumberList(req));
	}
	
	@ApiOperLog(action = ACTION.SAVE)
	@ResponseBody
	@RequestMapping("saveSerialNumber")
	public Result<?> saveSerialNumber(@Valid @RequestBody ReqSaveSerialNumber req) throws Exception {
		serialNumberService.saveSerialNumber(req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperLog(action = ACTION.DELETE)
	@ResponseBody
	@RequestMapping("removeSerialNumber")
	public Result<?> removeSerialNumber(@Valid @RequestBody List<ReqRemoveSerialNumber> req) throws Exception {
		serialNumberService.removeSerialNumber(req);
		return ResultMessage.SUCCESS.result();
	}
	
	
}
