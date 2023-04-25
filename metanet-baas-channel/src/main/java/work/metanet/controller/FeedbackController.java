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
import work.metanet.api.feedback.IFeedbackService;
import work.metanet.api.feedback.protocol.ReqFeedbackList;
import work.metanet.api.feedback.protocol.ReqFeedbackList.RespFeedbackList;
import work.metanet.base.BaseController;
import work.metanet.base.RespPaging;
import work.metanet.base.Result;
import work.metanet.base.ResultMessage;

@ApiModule(module = Module.FEEDBACK)
@Controller
@RequestMapping("feedback")
public class FeedbackController extends BaseController{
	
	@DubboReference IFeedbackService feedbackService;
	
	@RequestMapping("listPage")
	public String listPage() {
		return "page/feedback/list";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("feedbackList")
	public Result<RespPaging<RespFeedbackList>> feedbackList(@RequestBody ReqFeedbackList req) throws Exception {
		req.setChannelId(getChannelId());
		return ResultMessage.SUCCESS.result(feedbackService.feedbackList(req));
	}
	
}
