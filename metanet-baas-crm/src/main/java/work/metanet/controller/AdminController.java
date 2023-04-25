package work.metanet.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import work.metanet.aop.ApiModule;
import work.metanet.aop.ApiModule.Module;
import work.metanet.aop.ApiOperLog;
import work.metanet.aop.ApiOperLog.ACTION;
import work.metanet.aop.ApiPermission;
import work.metanet.aop.ApiPermission.AUTH;
import work.metanet.api.admin.IAdminService;
import work.metanet.api.admin.protocol.ReqAdminInfo;
import work.metanet.api.admin.protocol.ReqAdminInfo.RespAdminInfo;
import work.metanet.api.admin.protocol.ReqAdminList;
import work.metanet.api.admin.protocol.ReqAdminList.RespAdminList;
import work.metanet.api.admin.protocol.ReqAdminLogin;
import work.metanet.api.admin.protocol.ReqAdminLogin.RespAdminLogin;
import work.metanet.api.admin.protocol.ReqRemoveAdmin;
import work.metanet.api.admin.protocol.ReqResetAdminPassword;
import work.metanet.api.admin.protocol.ReqSaveAdmin;
import work.metanet.api.admin.protocol.ReqUpdAdminPassword;
import work.metanet.base.BaseController;
import work.metanet.base.RespPaging;
import work.metanet.base.Result;
import work.metanet.base.ResultMessage;
import work.metanet.utils.HttpServletRequestUtil;

@ApiModule(module = Module.ADMIN)
@Controller
@RequestMapping("admin")
@CrossOrigin
public class AdminController extends BaseController{

	@DubboReference
	private IAdminService adminService;
	
	@ApiPermission(AUTH.OPEN)
	@ApiOperLog(action = ACTION.LOGIN)
	@ResponseBody
	@RequestMapping("login")
	public Result<?> login(@RequestBody ReqAdminLogin req) throws Exception {
		RespAdminLogin admin = adminService.login(req);
		//登录成功后缓存sessionId,控制后台用户只能登入一端
		adminService.cacheSession(admin.getAdminId(), HttpServletRequestUtil.getRequest().getSession().getId());
		
		HttpServletRequestUtil.getRequest().getSession().setAttribute("admin", admin);
		
		//System.out.println(HttpServletRequestUtil.getRequest().getSession().getId()+"---------"+HttpServletRequestUtil.getRequest().getSession().getServletContext().getSessionTimeout());
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiPermission(AUTH.OPEN)
	@ResponseBody
	@RequestMapping("menu")
	public String menu() {
		return getAdmin().getJsonMenus();
	}
	
	@ApiPermission(AUTH.OPEN)
	@RequestMapping("updPwdPage")
	public String updPwdPage() {
		return "page/admin/updPwd";
	}
	
	@ApiPermission(AUTH.OPEN)
	@ApiOperLog(action = ACTION.UPDATE_PWD)
	@ResponseBody
	@RequestMapping("updAdminPassword")
	public Result<?> updAdminPassword(@Valid @RequestBody ReqUpdAdminPassword req) throws Exception{
		adminService.updAdminPassword(getAdminId(), req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiPermission(AUTH.OPEN)
	@ApiOperLog(action = ACTION.LOGOUT)
	@RequestMapping("logout")
	public String logout(HttpServletRequest request){
		request.getSession().removeAttribute("admin");
		return "redirect:/";
	}
	
	
	@RequestMapping("listPage")
	public String listPage() {
		return "page/admin/list";
	}
	
	@RequestMapping("editPage")
	public String editPage(HttpServletRequest request,String adminId) throws Exception {
		RespAdminInfo info = new RespAdminInfo();
		if(StringUtils.isNotBlank(adminId)) {
			info = adminService.adminInfo(new ReqAdminInfo().setAdminId(adminId));
		}
		request.setAttribute("info", info);
		return "page/admin/edit";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("adminList")
	public Result<RespPaging<RespAdminList>> adminList(@RequestBody ReqAdminList req) throws Exception {
		return ResultMessage.SUCCESS.result(adminService.adminList(req));
	}
	
	@ApiOperLog(action = ACTION.SAVE)
	@ResponseBody
	@RequestMapping("saveAdmin")
	public Result<?> saveAdmin(@Valid @RequestBody ReqSaveAdmin req) throws Exception {
		adminService.saveAdmin(req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperLog(action = ACTION.DELETE)
	@ResponseBody
	@RequestMapping("removeAdmin")
	public Result<?> removeAdmin(@Valid @RequestBody List<ReqRemoveAdmin> req) throws Exception {
		adminService.removeAdmin(req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperLog(action = ACTION.RESET_PWD)
	@ResponseBody
	@RequestMapping("resetAdminPassword")
	public Result<?> resetAdminPassword(@Valid @RequestBody ReqResetAdminPassword req) throws Exception {
		adminService.resetAdminPassword(req.getAdminId());
		return ResultMessage.SUCCESS.result();
	}
	
}
