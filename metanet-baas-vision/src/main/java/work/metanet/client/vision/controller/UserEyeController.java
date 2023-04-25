
package work.metanet.client.vision.controller;

import java.util.List;

import javax.validation.Valid;

import work.metanet.api.user.protocol.ReqUserInfo;
import work.metanet.model.UserFromThird;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import work.metanet.aop.ApiOperLog;
import work.metanet.aop.ApiOperLog.ACTION;
import work.metanet.api.user.IUserService;
import work.metanet.api.user.protocol.ReqSyncUserFromThird;
import work.metanet.api.user.protocol.ReqSyncUserFromThird.RespSyncUserFromThird;
import work.metanet.api.vision.IEyeService;
import work.metanet.api.vision.protocol.ReqEyeInfo;
import work.metanet.api.vision.protocol.ReqSaveEye;
import work.metanet.api.vision.protocol.ReqSaveEye.RespSaveEye;
import work.metanet.api.vision.protocol.ReqEyeInfo.RespEyeInfo;
import work.metanet.api.vision.protocol.ReqEyeList;
import work.metanet.api.vision.protocol.ReqEyeList.RespEyeList;
import work.metanet.api.vision.protocol.ReqRemoveEye;
import work.metanet.base.RespPaging;
import work.metanet.base.Result;
import work.metanet.base.ResultMessage;
import work.metanet.constant.ConstEyeType;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Description 控制器：用户眼睛信息
 * @author EdisonFeng
 * @DateTime 2021年4月25日
 * Copyright(c) 2021. All Rights Reserved
 */
@Api(tags={"用户眼睛基本信息"})
@RestController
@RequestMapping("api/userEye")
public class UserEyeController extends BaseController {
	@DubboReference 
	private IEyeService eyeService;
	@DubboReference
	private IUserService userService;

	@ApiOperation(value="用户眼睛信息查询")
	@PostMapping("userEye")
	public Result<?> getEyeInfo(@Valid @RequestBody ReqEyeInfo req) throws Exception {
		if(StringUtils.isBlank(req.getUserId()) && StringUtils.isBlank(req.getEyeId())) {
			return ResultMessage.FAILURE.result().setMsg("userId和eyeId不能同时为空");
		}
		
		RespEyeInfo resp = null;
		try {
			resp = eyeService.getEyeInfo(req);
		} 
		catch(Exception e) {
			String msg = e.getMessage();
			if(StringUtils.isBlank(msg)) msg = "未知异常";
			return ResultMessage.FAILURE.result(resp).setMsg(msg);
		}

		return ResultMessage.SUCCESS.result(resp);
	}


	@ApiOperation(value = "从第三方参数获取UserId")
	@PostMapping("/userInfoFromThird")
	public Result<?> userInfoFromTel(@Valid @RequestBody UserFromThird uft)throws Exception{
		ReqUserInfo.RespUserInfo respUserInfo = userService.userInfoFromThird(uft);
		return ResultMessage.SUCCESS.result(respUserInfo);
	}
	
	@ApiOperation(value="第三方用户同步")
	@PostMapping("syncUser")
	public Result<RespSyncUserFromThird> syncUser(@Valid @RequestBody ReqSyncUserFromThird req) throws Exception{
		RespSyncUserFromThird resp = null;
		RespSaveEye respSaveLeftEye = null;
		RespSaveEye respSaveRightEye = null;
		try {
			// save user info
			resp = userService.syncUserMore(req);
			if(BeanUtil.isEmpty(resp)) {
				return ResultMessage.FAILURE.result(resp).setMsg("用户同步异常");
			}
			resp.setAppID(req.getAppID());

			List<RespEyeInfo> respEyeInfos = eyeService.eyesInfoByUser(resp.getUserId());

			if (respEyeInfos.size() != 0 && respEyeInfos != null) {
				for (RespEyeInfo respEyeInfo : respEyeInfos) {
					int type = respEyeInfo.getType();
					String eyeId = respEyeInfo.getEyeId();
					if (type == 0) {
						resp.setLeftEyeID(eyeId);
					}
					if (type == 1){
						resp.setRightEyeID(eyeId);
					}
				}
				return ResultMessage.SUCCESS.result(resp);
			}

			
			// save left eye info
			ReqSaveEye reqSaveLeftEye = new ReqSaveEye();
			reqSaveLeftEye.setUserId(resp.getUserId());
			reqSaveLeftEye.setType(ConstEyeType.LEFT_EYE.ordinal());
			respSaveLeftEye = eyeService.saveEye(reqSaveLeftEye);
			if(BeanUtil.isEmpty(respSaveLeftEye)) {
				return ResultMessage.FAILURE.result(resp).setMsg("同步左眼信息异常");
			}
			resp.setLeftEyeID(respSaveLeftEye.getEyeId());

			// save right eye info
			ReqSaveEye reqSaveRightEye = new ReqSaveEye();
			reqSaveRightEye.setUserId(resp.getUserId());
			reqSaveRightEye.setType(ConstEyeType.RIGHT_EYE.ordinal());
			respSaveRightEye = eyeService.saveEye(reqSaveRightEye);
			if(BeanUtil.isEmpty(respSaveRightEye)) {
				return ResultMessage.FAILURE.result(resp).setMsg("同步右眼信息异常");
			}
			resp.setRightEyeID(respSaveRightEye.getEyeId());
		}
		catch(Exception e) {
			String msg = e.getMessage();
			if(StringUtils.isBlank(msg)) msg = "未知异常";
			return ResultMessage.FAILURE.result(resp).setMsg(msg);
		}
		return ResultMessage.SUCCESS.result(resp);
	}
	
	@ApiOperation(value="保存眼睛信息（新增、修改）")
	@PostMapping("saveEye")
	public Result<RespSaveEye> saveEyeInfo(@RequestBody ReqSaveEye req) throws Exception {
		RespSaveEye resp = null;
		try {
			resp = eyeService.saveEye(req);
		}
		catch(Exception e) {
			String msg = e.getMessage();
			if(StringUtils.isBlank(msg)) msg = "未知异常";
			return ResultMessage.FAILURE.result(resp).setMsg(msg);
		}
		return ResultMessage.SUCCESS.result(resp);
	}
	
	@ApiOperation(value="删除眼睛信息（需授权）")
	@ApiOperLog(action = ACTION.DELETE)
	@PostMapping("removeEye")
	public Result<String> removeEye(@Valid @RequestBody List<ReqRemoveEye> req) throws Exception {
		String resp = null;
		try {
			resp = eyeService.removeEye(req);
		}
		catch(Exception e) {
			String msg = e.getMessage();
			if(StringUtils.isBlank(msg)) msg = "未知异常";
			return ResultMessage.FAILURE.result(resp).setMsg(msg);
		}
		return ResultMessage.SUCCESS.result(resp);
	}
	
	@ApiOperation(value="获取眼睛信息列表")
	@PostMapping("getEyeList")
	public Result<RespPaging<RespEyeList>> getEyeList(@RequestBody ReqEyeList req) throws Exception {
		RespPaging<RespEyeList> resp = null;
		try {
			resp = eyeService.getEyeList(req);
		}
		catch(Exception e) {
			String msg = e.getMessage();
			if(StringUtils.isBlank(msg)) msg = "未知异常";
			return ResultMessage.FAILURE.result(resp).setMsg(msg);
		}
		return ResultMessage.SUCCESS.result(resp);
	}
}
