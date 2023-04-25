package work.metanet.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import work.metanet.aop.ApiModule;
import work.metanet.aop.ApiModule.Module;
import work.metanet.aop.ApiOperLog;
import work.metanet.aop.ApiOperLog.ACTION;
import work.metanet.aop.ApiPermission;
import work.metanet.aop.ApiPermission.AUTH;
import work.metanet.api.channel.IChannelService;
import work.metanet.api.channel.protocol.ReqChannelLogin;
import work.metanet.api.channel.protocol.ReqChannelLogin.RespChannelLogin;
import work.metanet.api.channel.protocol.ReqUpdChannelPassword;
import work.metanet.base.BaseController;
import work.metanet.base.Result;
import work.metanet.base.ResultMessage;
import work.metanet.utils.HttpServletRequestUtil;

@ApiModule(module = Module.CHANNEL)
@Controller
@RequestMapping("channel")
public class ChannelController extends BaseController{

	@DubboReference
	private IChannelService channelService;
	
	@ApiPermission(AUTH.OPEN)
	@ApiOperLog(action = ACTION.LOGIN)
	@ResponseBody
	@RequestMapping("login")
	public Result<?> login(@RequestBody ReqChannelLogin req) throws Exception {
		RespChannelLogin channel = channelService.login(req);
		//登录成功后缓存sessionId,控制后台用户只能登入一端
		channelService.cacheSession(channel.getChannelId(), HttpServletRequestUtil.getRequest().getSession().getId());
		
		HttpServletRequestUtil.getRequest().getSession().setAttribute("channel", channel);
		
		//System.out.println(HttpServletRequestUtil.getRequest().getSession().getId()+"---------"+HttpServletRequestUtil.getRequest().getSession().getServletContext().getSessionTimeout());
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiPermission(AUTH.OPEN)
	@ResponseBody
	@RequestMapping("menu")
	public String menu() {
		return getChannel().getJsonMenus();
	}
	
	@ApiPermission(AUTH.OPEN)
	@RequestMapping("updPwdPage")
	public String updPwdPage() {
		return "page/channel/updPwd";
	}
	
	@ApiPermission(AUTH.OPEN)
	@ApiOperLog(action = ACTION.UPDATE_PWD)
	@ResponseBody
	@RequestMapping("updChannelPassword")
	public Result<?> updChannelPassword(@Valid @RequestBody ReqUpdChannelPassword req) throws Exception{
		channelService.updChannelPassword(getChannelId(), req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiPermission(AUTH.OPEN)
	@ApiOperLog(action = ACTION.LOGOUT)
	@RequestMapping("logout")
	public String logout(HttpServletRequest request){
		request.getSession().removeAttribute("channel");
		return "redirect:/";
	}
	
}
