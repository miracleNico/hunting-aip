
package work.metanet.client.vision.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import work.metanet.aop.ApiOperLog;
import work.metanet.aop.ApiOperLog.ACTION;
import work.metanet.api.channel.protocol.ReqChannelList;
import work.metanet.api.channel.protocol.ReqChannelList.RespChannelList;
import work.metanet.api.vision.IEyeService;
import work.metanet.api.vision.IVisionTest;
import work.metanet.api.vision.protocol.ReqRemoveVisionTest;
import work.metanet.api.vision.protocol.ReqSaveVisionTest;
import work.metanet.api.vision.protocol.ReqSaveVisionTest.RespSaveVisionTest;
import work.metanet.api.vision.protocol.ReqVisionTestInfo;
import work.metanet.api.vision.protocol.ReqVisionTestInfo.RespVisionTestInfo;
import work.metanet.api.vision.protocol.ReqVisionTestList;
import work.metanet.api.vision.protocol.ReqVisionTestList.RespVisionTestList;
import work.metanet.base.RespPaging;
import work.metanet.base.Result;
import work.metanet.base.ResultMessage;
import work.metanet.exception.LxException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 * @Description 控制器：视力测试活动
 * @author EdisonFeng
 * @DateTime 2021年4月25日
 * Copyright(c) 2021. All Rights Reserved
 */
@Api(tags={"视力测试活动"})
@RestController
@RequestMapping("api/visionTest")
public class VisionTestController extends BaseController {
	@DubboReference
	private IEyeService eyeService;
	@DubboReference
	private IVisionTest visionTestService;

	@ApiOperation(value="视力测试活动详情")
	@PostMapping("visionTestInfo")
	public Result<RespVisionTestInfo> getVisionTestInfo(@Valid @RequestBody ReqVisionTestInfo req) throws Exception {
		RespVisionTestInfo resp = null;
		try {
			resp = visionTestService.getVisionTestInfo(req);
		}
		catch(Exception e) {
			String msg = e.getMessage();
			if(StringUtils.isBlank(msg)) msg = "未知异常";
			return ResultMessage.FAILURE.result(resp).setMsg(msg);
		}
		return ResultMessage.SUCCESS.result(resp);
	}
	
	@ApiOperation(value="保存视力测试活动（新增、修改）")
	@PostMapping("saveVisionTest")
	public Result<RespSaveVisionTest> saveVisionTest(@RequestBody ReqSaveVisionTest req) throws Exception {
		RespSaveVisionTest resp = null;
		try {
			resp = visionTestService.saveVisionTest(req);
		}
		catch(Exception e) {
			String msg = e.getMessage();
			if(StringUtils.isBlank(msg)) msg = "未知异常";
			return ResultMessage.FAILURE.result(resp).setMsg(msg);
		}
		
		return ResultMessage.SUCCESS.result(resp);
	}
	
	@ApiOperation(value="删除视力测试活动（需授权）")
	@PostMapping("removeVisionTest")
	public Result<String> removeVisionTest(@Valid @RequestBody List<ReqRemoveVisionTest> req) throws Exception {
		String resp = null;
		try {
			resp = visionTestService.removeVisionTest(req);
		}
		catch(Exception e) {
			String msg = e.getMessage();
			if(StringUtils.isBlank(msg)) msg = "未知异常";
			return ResultMessage.FAILURE.result(resp).setMsg(msg);
		}
		return ResultMessage.SUCCESS.result(resp);
	}

	@ApiOperation(value="获取视力测试活动列表")
	@PostMapping("getVisionTestList")
	public Result<RespPaging<RespVisionTestList>> getVisionTestList(@RequestBody ReqVisionTestList req) throws Exception {
		RespPaging<RespVisionTestList> resp = null;
		try {
			resp = visionTestService.getVisionTestList(req);
		}
		catch(Exception e) {
			String msg = e.getMessage();
			if(StringUtils.isBlank(msg)) msg = "未知异常";
			return ResultMessage.FAILURE.result(resp).setMsg(msg);
		}
		return ResultMessage.SUCCESS.result(resp);
	}
}
