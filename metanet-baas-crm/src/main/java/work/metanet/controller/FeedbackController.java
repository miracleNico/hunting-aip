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
import work.metanet.api.feedback.IFeedbackService;
import work.metanet.api.feedback.protocol.ReqFeedbackInfo;
import work.metanet.api.feedback.protocol.ReqFeedbackInfo.RespFeedbackInfo;
import work.metanet.api.feedback.protocol.ReqFeedbackList;
import work.metanet.api.feedback.protocol.ReqFeedbackList.RespFeedbackList;
import work.metanet.api.feedback.protocol.ReqFeedbakProcessStatus;
import work.metanet.api.feedback.protocol.ReqRemoveFeedback;
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
	
	@RequestMapping("editPage")
	public String editPage(HttpServletRequest request,String feedbackId) throws Exception {
		RespFeedbackInfo info = new RespFeedbackInfo();
		if(StringUtils.isNotBlank(feedbackId)) {
			info = feedbackService.feedbackInfo(new ReqFeedbackInfo().setFeedbackId(feedbackId));
		}
		request.setAttribute("info", info);
		return "page/feedback/edit";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("feedbackList")
	public Result<RespPaging<RespFeedbackList>> feedbackList(@RequestBody ReqFeedbackList req) throws Exception {
		return ResultMessage.SUCCESS.result(feedbackService.feedbackList(req));
	}
	
	@ApiOperLog(action = ACTION.DELETE)
	@ResponseBody
	@RequestMapping("removeFeedback")
	public Result<?> removeFeedback(@Valid @RequestBody List<ReqRemoveFeedback> req) throws Exception {
		feedbackService.removeFeedback(req);
		return ResultMessage.SUCCESS.result();
	}
	
	/**
	 * @Description: 问题反馈处理
	 * @Author wanbo
	 * @DateTime 2020/03/12
	 */
	@ApiOperLog(action = ACTION.UPDATE)
	@ResponseBody
	@RequestMapping("feedbakProcessStatus")
	public Result<?> feedbakProcessStatus(@Valid @RequestBody ReqFeedbakProcessStatus req) throws Exception {
		feedbackService.feedbakProcessStatus(req);
		return ResultMessage.SUCCESS.result();
	}
	
	
}
