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
import work.metanet.api.user.IUserService;
import work.metanet.api.user.protocol.ReqUserList;
import work.metanet.api.user.protocol.ReqUserList.RespUserList;
import work.metanet.base.BaseController;
import work.metanet.base.RespPaging;
import work.metanet.base.Result;
import work.metanet.base.ResultMessage;

import cn.hutool.core.util.StrUtil;

@ApiModule(module = Module.USER)
@Controller
@RequestMapping("user")
public class UserController extends BaseController{
	
	@DubboReference IUserService userService;
	
	@RequestMapping("listPage")
	public String listPage() {
		return "page/user/list";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("userList")
	public Result<RespPaging<RespUserList>> userList(@RequestBody ReqUserList req) throws Exception {
		RespPaging<RespUserList> respPaging = userService.userList(req);
		for (RespUserList resp : respPaging.getList()) {
			resp.setPhone(StrUtil.replace(resp.getPhone(), 3, 7, '*'));
		}
		return ResultMessage.SUCCESS.result(respPaging);
	}
	
	/**
	@ApiOperLog(action = ACTION.DELETE)
	@ResponseBody
	@RequestMapping("removeUser")
	public Result<?> removeUser(@Valid @RequestBody List<ReqRemoveUser> req) throws Exception {
		userService.removeUser(req);
		return ResultMessage.SUCCESS.result();
	}*/
	
	
}
