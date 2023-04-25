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
import work.metanet.aop.ApiPermission;
import work.metanet.aop.ApiPermission.AUTH;
import work.metanet.api.role.IRoleService;
import work.metanet.api.role.protocol.ReqRemoveRole;
import work.metanet.api.role.protocol.ReqRoleInfo;
import work.metanet.api.role.protocol.ReqRoleInfo.RespRoleInfo;
import work.metanet.api.role.protocol.ReqRoleList;
import work.metanet.api.role.protocol.ReqRoleList.RespRoleList;
import work.metanet.api.role.protocol.ReqSaveRole;
import work.metanet.base.BaseController;
import work.metanet.base.RespPaging;
import work.metanet.base.Result;
import work.metanet.base.ResultMessage;

@ApiModule(module = Module.ROLE)
@Controller
@RequestMapping("role")
public class RoleController extends BaseController{
	
	@DubboReference IRoleService roleService;
	
	@RequestMapping("listPage")
	public String listPage() {
		return "page/role/list";
	}
	
	@RequestMapping("editPage")
	public String editPage(HttpServletRequest request,String roleId) throws Exception {
		RespRoleInfo info = new RespRoleInfo();
		if(StringUtils.isNotBlank(roleId)) {
			info = roleService.roleInfo(new ReqRoleInfo().setRoleId(roleId));
		}
		request.setAttribute("info", info);
		return "page/role/edit";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("roleList")
	public Result<RespPaging<RespRoleList>> roleList(@RequestBody ReqRoleList req) throws Exception {
		return ResultMessage.SUCCESS.result(roleService.roleList(req));
	}
	
	@ApiPermission(AUTH.OPEN)
	@ResponseBody
	@RequestMapping("roleSelected")
	public Result<RespPaging<RespRoleList>> roleSelected(@RequestBody ReqRoleList req) throws Exception {
		return ResultMessage.SUCCESS.result(roleService.roleList(req));
	}
	
	@ApiOperLog(action = ACTION.SAVE)
	@ResponseBody
	@RequestMapping("saveRole")
	public Result<?> saveRole(@Valid @RequestBody ReqSaveRole req) throws Exception {
		roleService.saveRole(req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperLog(action = ACTION.DELETE)
	@ResponseBody
	@RequestMapping("removeRole")
	public Result<?> removeRole(@Valid @RequestBody List<ReqRemoveRole> req) throws Exception {
		roleService.removeRole(req);
		return ResultMessage.SUCCESS.result();
	}
	
	
}
