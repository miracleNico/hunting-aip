package work.metanet.server.service;


import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import work.metanet.api.role.IRoleService;
import work.metanet.api.role.protocol.ReqRemoveRole;
import work.metanet.api.role.protocol.ReqRoleInfo;
import work.metanet.api.role.protocol.ReqRoleInfo.RespRoleInfo;
import work.metanet.api.role.protocol.ReqRoleList;
import work.metanet.api.role.protocol.ReqRoleList.RespRoleList;
import work.metanet.api.role.protocol.ReqSaveRole;
import work.metanet.base.RespPaging;
import work.metanet.base.ResultMessage;
import work.metanet.constant.ConstRoleType;
import work.metanet.exception.DaasException;
import work.metanet.model.Role;
import work.metanet.server.dao.RoleMapper;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.IdUtil;
import io.seata.spring.annotation.GlobalTransactional;

@DubboService
public class RoleService implements IRoleService{

	@Autowired
	private RoleMapper roleMapper;
	
	/**
	 * @Description: 角色列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespPaging<RespRoleList> roleList(ReqRoleList req) throws Exception {
		RespPaging<RespRoleList> respPaging = new RespPaging<RespRoleList>();
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<RespRoleList> list = roleMapper.roleList(BeanUtil.beanToMap(req));
		BeanUtil.copyProperties(new PageInfo<RespRoleList>(list), respPaging);
		for (RespRoleList resp : respPaging.getList()) {
			resp.setRoleTypeName(EnumUtil.fromString(ConstRoleType.class, resp.getRoleType()).getText());
		}
		return respPaging;
	}

	/**
	 * @Description: 角色信息
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespRoleInfo roleInfo(ReqRoleInfo req) throws Exception {
		return roleMapper.roleInfo(BeanUtil.beanToMap(req));
	}

	
	/**
	 * @Description: 保存角色
	 * @Author wanbo
	 * @DateTime 2020/06/16
	 */
	@Override
	@Transactional
	public void saveRole(ReqSaveRole req) throws Exception {
		Role role = new Role();
		BeanUtil.copyProperties(req, role);
		Role dbRole = roleMapper.roleOnly(MapUtil.builder("roleName",req.getRoleName()).build());
		//修改操作
		if(StringUtils.isNotBlank(req.getRoleId())) {
			if(!BeanUtil.isEmpty(dbRole) && !dbRole.getRoleId().equals(req.getRoleId())) throw DaasException.of().setMsg("角色已存在");
			if(BeanUtil.isEmpty(dbRole) || dbRole.getRoleId().equals(req.getRoleId())) {
				roleMapper.updateByPrimaryKeySelective(role);
			}else {
				throw DaasException.of().setResult(ResultMessage.FAILURE.result());
			}
		}else {//新增操作
			if(!BeanUtil.isEmpty(dbRole)) throw DaasException.of().setMsg("角色已存在");
			role.setRoleId(IdUtil.fastSimpleUUID());
			roleMapper.insertSelective(role);
		}
		
		//角色权限
		roleMapper.removeRolePermission(role.getRoleId());
		if(CollUtil.isNotEmpty(req.getPermissionList())) {
			roleMapper.addRolePermission(role.getRoleId(), req.getPermissionList());
		}
	}
	
	/**
	 * @Description: 删除角色
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@GlobalTransactional
	@Override
	public void removeRole(List<ReqRemoveRole> req) throws Exception {
		roleMapper.removeRole(req);
		for (ReqRemoveRole role : req) {
			roleMapper.removeRolePermission(role.getRoleId());
			roleMapper.removeRoleAdmin(role.getRoleId());
		}
	}
	
	@Override
	public int addAdminRole(String adminId, List<String> roleIdList) {
		return roleMapper.addAdminRole(adminId, roleIdList);
	}
	
	@Override
	public List<String> getRoleIdListByAdminId(String adminId) {
		return roleMapper.getRoleIdListByAdminId(adminId);
	}
	
	@Override
	public int removeAdminRole(String adminId) {
		return roleMapper.removeAdminRole(adminId);
	}
	
}
