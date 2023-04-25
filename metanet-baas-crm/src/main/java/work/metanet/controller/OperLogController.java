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
import work.metanet.api.log.ISystemLogService;
import work.metanet.api.log.protocol.ReqOperLogList;
import work.metanet.api.log.protocol.ReqOperLogList.RespOperLogList;
import work.metanet.base.BaseController;
import work.metanet.base.RespPaging;
import work.metanet.base.Result;
import work.metanet.base.ResultMessage;

@ApiModule(module = Module.LOG)
@Controller
@RequestMapping("log")
public class OperLogController extends BaseController{
	
	@DubboReference
	private ISystemLogService systemLogService;
	
	@RequestMapping("operLogPage")
	public String operLogPage() {
		return "page/log/operLog";
	}
	
	@ApiOperLog(action = ACTION.SELECT, desc = "操作日志")
	@ResponseBody
	@RequestMapping("operLogList")
	public Result<RespPaging<RespOperLogList>> operLogList(@RequestBody ReqOperLogList req) throws Exception {
		return ResultMessage.SUCCESS.result(systemLogService.operLogList(req));
	}
	
}
