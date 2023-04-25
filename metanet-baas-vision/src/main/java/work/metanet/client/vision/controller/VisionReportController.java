package work.metanet.client.vision.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import work.metanet.api.vision.IVisionExercise;
import work.metanet.api.vision.protocol.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import work.metanet.api.vision.IVisionCondition;
import work.metanet.api.vision.IVisionReport;
import work.metanet.api.vision.IVisionTest;
import work.metanet.api.vision.protocol.ReqRecommendVisionReport.RespRecommendReportList;
import work.metanet.api.vision.protocol.ReqSaveVisionReport.RespSaveVisionReport;
import work.metanet.api.vision.protocol.ReqSearchVisionReport.RespSearchReportList;
import work.metanet.api.vision.protocol.ReqVisionCondition.RespVisionCondition;
import work.metanet.api.vision.protocol.ReqVisionReportInfo.RespVisionReportInfo;
import work.metanet.api.vision.protocol.ReqVisionReportList.RespVisionReportList;
import work.metanet.base.RespPaging;
import work.metanet.base.Result;
import work.metanet.base.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags={"视力报告"})
@RestController
@RequestMapping("api/visionReport")
public class VisionReportController extends BaseController {
	
	@DubboReference
	private IVisionReport visionReportService;
	@DubboReference
	private IVisionTest visionTestService;
	@DubboReference
	private IVisionCondition visionConditionService;
	@DubboReference
	private IVisionExercise visionExerciseService;
	
	@ApiOperation(value="视力报告筛选条件")
	@PostMapping("visionCondition")
	public Result<List<RespVisionCondition>> getVisionCondition(@Valid @RequestBody ReqVisionCondition req) throws Exception {
		List<RespVisionCondition> resp = null;
		try {
			resp = visionConditionService.getVisionCondition(req);
		}
		catch(Exception e) {
			String msg = e.getMessage();
			if(StringUtils.isBlank(msg)) msg = "未知异常";
			return ResultMessage.FAILURE.result(resp).setMsg(msg);
		}
		return ResultMessage.SUCCESS.result(resp);

	}
	
	@ApiOperation(value="推荐视力报告")
	@PostMapping("recommendVisionReport")
	public Result<RespPaging<RespRecommendReportList>> recommendVisionReport(@Valid @RequestBody ReqRecommendVisionReport req) throws Exception {
		RespPaging<RespRecommendReportList> resp = null;
		try {
			resp = visionReportService.recommendVisionReport(req);
		}
		catch(Exception e) {
			String msg = e.getMessage();
			if(StringUtils.isBlank(msg)) msg = "未知异常";
			return ResultMessage.FAILURE.result(resp).setMsg(msg);
		}
		return ResultMessage.SUCCESS.result(resp);
	}
	
	@ApiOperation(value="视力报告搜索")
	@PostMapping("searchReport")
	public Result<RespPaging<RespSearchReportList>> searchReport(@Valid @RequestBody ReqSearchVisionReport req) throws Exception {
		RespPaging<RespSearchReportList> resp = null;
		if(StringUtils.isBlank(req.getKeyword())
				&& StringUtils.isBlank(req.getOwnerId())
				&& StringUtils.isBlank(req.getUserId())) {
			return ResultMessage.FAILURE_REQUEST_PARAM.result(resp).setMsg("关键词、UserID和OwnerID参数不能都为空");
		}
		
		try {
			resp = visionReportService.searchVisionReport(req);
		}
		catch(Exception e) {
			String msg = e.getMessage();
			if(StringUtils.isBlank(msg)) msg = "未知异常";
			return ResultMessage.FAILURE.result(resp).setMsg(msg);
		}
		return ResultMessage.SUCCESS.result(resp);
	}
	
	@ApiOperation(value="视力报告详情")
	@PostMapping("visionReportInfo")
	public Result<RespVisionReportInfo> getVisionReportInfo(@Valid @RequestBody ReqVisionReportInfo req) throws Exception {
		RespVisionReportInfo resp = null;
		try {
			resp = visionReportService.getVisionReportInfo(req);
		}
		catch(Exception e) {
			String msg = e.getMessage();
			if(StringUtils.isBlank(msg)) msg = "未知异常";
			return ResultMessage.FAILURE.result(resp).setMsg(msg);
		}
		return ResultMessage.SUCCESS.result(resp);
	}

	@ApiOperation(value = "视力报告列表")
	@ResponseBody
	@PostMapping("visionReportList")
	public Result<RespPaging<RespVisionReportList>> getVisionReportList(@Valid @RequestBody ReqVisionReportList req) throws Exception{
		RespPaging<RespVisionReportList> resp = null;
		try {
			resp = visionReportService.getVisionReportList(req);
		}
		catch(Exception e) {
			String msg = e.getMessage();
			if(StringUtils.isBlank(msg)) msg = "未知异常";
			return ResultMessage.FAILURE.result(resp).setMsg(msg);
		}
		return ResultMessage.SUCCESS.result(resp);
	}

	@ApiOperation(value = "近12次测试记录(只需userId)")
	@ResponseBody
	@PostMapping("twelveVisionReport")
	public Result<List<RespVisionReportList>> getTwelveVisionReport(@Valid @RequestBody ReqSearchVisionReport req) throws Exception {
		List<RespVisionReportList> resp = null;
		if (StringUtils.isBlank(req.getUserId())||StringUtils.isEmpty(req.getUserId())) {
			return ResultMessage.FAILURE.result(resp).setMsg("UserId不能为空");
		}
		try {
			resp = visionReportService.getTwelveVisionReport(req);
		} catch (Exception e) {
			String msg = e.getMessage();
			if (StringUtils.isBlank(msg)) msg = "未知异常";
			return ResultMessage.FAILURE.result(resp).setMsg(msg);
		}
		return ResultMessage.SUCCESS.result(resp);
	}

	@ApiOperation(value="保存视力报告（新增、修改）")
	@PostMapping("saveVisionReport")
	public Result<RespSaveVisionReport> saveVisionReport(@RequestBody ReqSaveVisionReport req) throws Exception {
		RespSaveVisionReport resp = null;
		try {
			resp = visionReportService.saveVisionReport(req);
		}
		catch(Exception e) {
			String msg = e.getMessage();
			if(StringUtils.isBlank(msg)) msg = "未知异常";
			return ResultMessage.FAILURE.result(resp).setMsg(msg);
		}
		return ResultMessage.SUCCESS.result(resp);
	}
	
	@ApiOperation(value="删除视力报告（需授权）")
	@PostMapping("removeVisionReport")
	public Result<String> removeVisionReport(@Valid @RequestBody List<ReqRemoveVisionReport> req) throws Exception {
		String resp = null;
		try {
			resp = visionReportService.removeVisionReport(req);
		}
		catch(Exception e) {
			String msg = e.getMessage();
			if(StringUtils.isBlank(msg)) msg = "未知异常";
			return ResultMessage.FAILURE.result(resp).setMsg(msg);
		}
		return ResultMessage.SUCCESS.result(resp);
	}

	@PostMapping("visionAVGAndExerciseCount")
	@ApiOperation(value = "获取过去七天双眼平均视力和每日锻炼次数")
	public Result<?> getVisionAVGAndExerciseCount(@Valid @RequestBody ReqVisionAVG req) throws Exception{
		List<ReqVisionAVG.RespVisionAVG> visionAVG = null;
		List<ReqVisionExerciseList.RespVisionExerciseList> visionExerciseLists = null;
		HashMap<String, Object> resp = new HashMap<>();
		try {
			visionAVG = visionReportService.getVisionAVG(req);
			resp.put("avg",visionAVG);
		} catch (Exception e) {
			return ResultMessage.FAILURE.result(visionAVG).setMsg("获取视力平均值异常");
		}
		try {
			visionExerciseLists = visionExerciseService.getExerciseCount(new ReqVisionExerciseList().setUserId(req.getUserId()));
			resp.put("count",visionExerciseLists);
		} catch (Exception e) {
			return ResultMessage.FAILURE.result().setMsg("获取每日锻炼次数异常");
		}
		return ResultMessage.SUCCESS.result(resp);
	}
	
}
