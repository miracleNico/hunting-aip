package work.metanet.controller;

import java.util.List;

import javax.validation.Valid;

import work.metanet.api.vision.protocol.*;
import work.metanet.api.vision.protocol.ReqAddVisionReportManually.RespAddVisionReportManually;
import work.metanet.api.vision.protocol.ReqEyeInfo.RespEyeInfo;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import work.metanet.api.user.IUserService;
import work.metanet.api.user.protocol.ReqSyncUserFromThird;
import work.metanet.api.user.protocol.ReqSyncUserFromThird.RespSyncUserFromThird;
import work.metanet.api.vision.IEyeService;
import work.metanet.api.vision.IVisionCondition;
import work.metanet.api.vision.IVisionReport;
import work.metanet.api.vision.IVisionTest;
import work.metanet.api.vision.protocol.ReqSaveEye.RespSaveEye;
import work.metanet.api.vision.protocol.ReqSaveVisionReport.RespSaveVisionReport;
import work.metanet.api.vision.protocol.ReqSaveVisionTest.RespSaveVisionTest;
import work.metanet.api.vision.protocol.ReqSearchVisionReport.RespSearchReportList;
import work.metanet.api.vision.protocol.ReqVisionReportInfo.RespVisionReportInfo;
import work.metanet.base.RespPaging;
import work.metanet.base.Result;
import work.metanet.base.ResultMessage;
import work.metanet.constant.ConstEyeType;
import cn.hutool.core.bean.BeanUtil;
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
	private IEyeService eyeService;
	@DubboReference
	private IUserService userService;
	
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

	@ApiOperation(value="家长端手工新增测试结果")
	@PostMapping("addVisionReportManually")
	public Result<RespAddVisionReportManually> addVisionReportManually(@RequestBody ReqAddVisionReportManually req) throws Exception {
		RespAddVisionReportManually resp = new RespAddVisionReportManually();
		RespSyncUserFromThird respSyncUserFromThird = null;
		RespSaveEye respSaveLeftEye = null;
		RespSaveEye respSaveRightEye = null;
		try {
			// save user info
			ReqSyncUserFromThird reqSyncUserFromThird = new ReqSyncUserFromThird();
			BeanUtil.copyProperties(req, reqSyncUserFromThird);
			respSyncUserFromThird = userService.syncUserMore(reqSyncUserFromThird);
			if(BeanUtil.isEmpty(respSyncUserFromThird)) {
				return ResultMessage.FAILURE.result(resp).setMsg("用户同步异常");
			}
			respSyncUserFromThird.setAppID(req.getAppID());
			
			//返回应用ID和用户ID
			resp.setAppID(respSyncUserFromThird.getAppID());
			resp.setUserId(respSyncUserFromThird.getUserId());

			//通过用户ID获取眼睛信息
			List<RespEyeInfo> respEyeInfos = eyeService.eyesInfoByUser(respSyncUserFromThird.getUserId());

			if (respEyeInfos.size() != 0 && respEyeInfos != null) {
				//存在眼睛信息，则获取左右眼ID
				for (RespEyeInfo respEyeInfo : respEyeInfos) {
					int type = respEyeInfo.getType();
					String eyeId = respEyeInfo.getEyeId();
					if (type == 0) {
						respSyncUserFromThird.setLeftEyeID(eyeId);
					}
					if (type == 1){
						respSyncUserFromThird.setRightEyeID(eyeId);
					}
				}
			}
			else {
				
				// save left eye info
				ReqSaveEye reqSaveLeftEye = new ReqSaveEye();
				reqSaveLeftEye.setUserId(respSyncUserFromThird.getUserId());
				reqSaveLeftEye.setType(ConstEyeType.LEFT_EYE.ordinal());
				respSaveLeftEye = eyeService.saveEye(reqSaveLeftEye);
				if(BeanUtil.isEmpty(respSaveLeftEye)) {
					return ResultMessage.FAILURE.result(resp).setMsg("同步左眼信息异常");
				}
				respSyncUserFromThird.setLeftEyeID(respSaveLeftEye.getEyeId());
	
				// save right eye info
				ReqSaveEye reqSaveRightEye = new ReqSaveEye();
				reqSaveRightEye.setUserId(respSyncUserFromThird.getUserId());
				reqSaveRightEye.setType(ConstEyeType.RIGHT_EYE.ordinal());
				respSaveRightEye = eyeService.saveEye(reqSaveRightEye);
				if(BeanUtil.isEmpty(respSaveRightEye)) {
					return ResultMessage.FAILURE.result(resp).setMsg("同步右眼信息异常");
				}
				respSyncUserFromThird.setRightEyeID(respSaveRightEye.getEyeId());
			}
			// 返回眼睛ID
			resp.setLeftEyeID(respSyncUserFromThird.getLeftEyeID());
			resp.setRightEyeID(respSyncUserFromThird.getRightEyeID());
			
			// 同步左眼测试活动
			ReqSaveVisionTest reqSaveVisionTestForLeftEye = new ReqSaveVisionTest();
			BeanUtil.copyProperties(req, reqSaveVisionTestForLeftEye);
			reqSaveVisionTestForLeftEye.setEyeId(respSyncUserFromThird.getLeftEyeID());
			RespSaveVisionTest respSaveVisionTestForLeftEye = null;
			respSaveVisionTestForLeftEye = visionTestService.saveVisionTest(reqSaveVisionTestForLeftEye);
			if(BeanUtil.isEmpty(respSaveVisionTestForLeftEye)) {
				return ResultMessage.FAILURE.result(resp).setMsg("同步左眼测试活动异常");
			}

			// 同步右眼测试活动
			ReqSaveVisionTest reqSaveVisionTestForRightEye = new ReqSaveVisionTest();
			BeanUtil.copyProperties(req, reqSaveVisionTestForRightEye);
			reqSaveVisionTestForLeftEye.setEyeId(respSyncUserFromThird.getLeftEyeID());
			RespSaveVisionTest respSaveVisionTestForRightEye = null;
			respSaveVisionTestForRightEye = visionTestService.saveVisionTest(reqSaveVisionTestForRightEye);
			if(BeanUtil.isEmpty(respSaveVisionTestForRightEye)) {
				return ResultMessage.FAILURE.result(resp).setMsg("同步右眼测试活动异常");
			}

			// 返回测试活动ID
			resp.setTestIdForLeftEye(respSaveVisionTestForLeftEye.getTestId());
			resp.setTestIdForRightEye(respSaveVisionTestForRightEye.getTestId());
			
			// 同步左眼测试结果
			ReqSaveVisionReport reqSaveVisionReportForLeftEye = new ReqSaveVisionReport();
			BeanUtil.copyProperties(req, reqSaveVisionReportForLeftEye);
			reqSaveVisionReportForLeftEye.setTestId(respSaveVisionTestForLeftEye.getTestId());
			RespSaveVisionReport respSaveVisionReportForLeftEye = null;
			respSaveVisionReportForLeftEye = visionReportService.saveVisionReport(reqSaveVisionReportForLeftEye);
			if(BeanUtil.isEmpty(respSaveVisionReportForLeftEye)) {
				return ResultMessage.FAILURE.result(resp).setMsg("同步左眼测试报告异常");
			}
			
			// 同步右眼测试结果
			ReqSaveVisionReport reqSaveVisionReportForRightEye = new ReqSaveVisionReport();
			BeanUtil.copyProperties(req, reqSaveVisionReportForRightEye);
			reqSaveVisionReportForLeftEye.setTestId(respSaveVisionTestForRightEye.getTestId());
			RespSaveVisionReport respSaveVisionReportForRightEye = null;
			respSaveVisionReportForRightEye = visionReportService.saveVisionReport(reqSaveVisionReportForRightEye);
			if(BeanUtil.isEmpty(respSaveVisionReportForRightEye)) {
				return ResultMessage.FAILURE.result(resp).setMsg("同步右眼测试报告异常");
			}
			
			// 返回测试结果ID
			resp.setReportIdForLeftEye(respSaveVisionReportForLeftEye.getReportId());
			resp.setReportIdForRightEye(respSaveVisionReportForRightEye.getReportId());
		}
		catch(Exception e) {
			String msg = e.getMessage();
			if(StringUtils.isBlank(msg)) msg = "未知异常";
			return ResultMessage.FAILURE.result(resp).setMsg(msg);
		}
		return ResultMessage.SUCCESS.result(resp);
	}
}
