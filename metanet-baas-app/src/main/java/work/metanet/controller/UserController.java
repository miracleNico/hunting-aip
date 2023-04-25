package work.metanet.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import work.metanet.api.user.IUserService;
import work.metanet.api.user.protocol.ReqAccountCancel;
import work.metanet.api.user.protocol.ReqCheckCode;
import work.metanet.api.user.protocol.ReqLogin.RespLogin;
import work.metanet.api.user.protocol.ReqLoginSuper;
import work.metanet.api.user.protocol.ReqResetPassword;
import work.metanet.api.user.protocol.ReqSendCode;
import work.metanet.api.user.protocol.ReqUpdPassword;
import work.metanet.api.user.protocol.ReqUpdUser;
import work.metanet.api.user.protocol.ReqUserInfo.RespUserInfo;
import work.metanet.base.BaseController;
import work.metanet.base.Result;
import work.metanet.base.ResultMessage;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags={"用户"})
@RestController
@RequestMapping("api/user")
public class UserController extends BaseController{
	
	@DubboReference
	private IUserService userService; 
	
	@ApiOperation(value="发送验证码")
	@PostMapping("sendCode")
	public Result<?> sendCode(@Valid @RequestBody ReqSendCode requestParam) throws Exception {
		userService.sendCode(getPackageName(),requestParam);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperation(value="校验验证码")
	@PostMapping("checkCode")
	public Result<?> checkCode(@Valid @RequestBody ReqCheckCode requestParam) throws Exception {
		userService.checkCode(getUserId(),requestParam);
		return ResultMessage.SUCCESS.result();
	}
	
	/**
	@ApiOperation(value="注册")
	@PostMapping("register")
	public Result<?> register(@Valid @RequestBody ReqRegister requestParam) throws Exception {
		userService.register(requestParam);
		return ResultMessage.SUCCESS.result();
	}*/
	
	/**
	@ApiOperation(value="登录")
	@PostMapping("login")
	public Result<RespLogin> login(@Valid @RequestBody ReqLogin requestParam) throws Exception {
		return ResultMessage.SUCCESS.result(userService.login(getDeviceId(),getPackageName(),getVersionCode(),requestParam));
	}
	*/
	
	@ApiOperation(value="登录与注册")
	@PostMapping("loginSuper")
	public Result<RespLogin> loginSuper(HttpServletRequest request,@Valid @RequestBody ReqLoginSuper requestParam) throws Exception {
		//获取deviceId兼容最初的版本
		String deviceId = requestParam.getDeviceId();
		RespLogin resp = userService.loginSuper(StrUtil.isNotBlank(deviceId)?deviceId:getDeviceId(),getPackageName(),getVersionCode(),requestParam);
		return ResultMessage.SUCCESS.result(resp);
	}
	
	
	
	
	@ApiOperation(value="修改用户信息")
	@PostMapping("updUser")
	public Result<?> updUser(@Valid @RequestBody ReqUpdUser requestParam) throws Exception {
		userService.updUser(getUserId(),requestParam);
		return ResultMessage.SUCCESS.result();
	}
	
	
	@ApiOperation(value="获取用户信息")
	@PostMapping("userInfo")
	public Result<RespUserInfo> userInfo() throws Exception {
		return ResultMessage.SUCCESS.result(userService.userInfo(getUserId()));
	}
	
	
	@ApiOperation(value="修改密码")
	@PostMapping("updPassword")
	public Result<?> updPassword(@Valid @RequestBody ReqUpdPassword requestParam) throws Exception {
		userService.updPassword(getUserId(),requestParam);
		return ResultMessage.SUCCESS.result();
	}
	
	
	@ApiOperation(value="重置密码")
	@PostMapping("resetPassword")
	public Result<?> resetPassword(@Valid @RequestBody ReqResetPassword requestParam) throws Exception {
		userService.resetPassword(getUserId(),requestParam);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperation(value="退出登录")
	@PostMapping("logout")
	public Result<?> logout() throws Exception {
		userService.logout(getUserId());
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperation(value="销户")
	@PostMapping("accountCancel")
	public Result<?> accountCancel(@Valid @RequestBody ReqAccountCancel req) throws Exception {
		userService.accountCancel(getUserId(), req);
		return ResultMessage.SUCCESS.result();
	}
	
}
