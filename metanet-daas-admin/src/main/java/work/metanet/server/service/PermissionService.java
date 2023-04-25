package work.metanet.server.service;


import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import work.metanet.api.permission.IPermissionService;
import work.metanet.api.permission.protocol.ReqPermissionInfo;
import work.metanet.api.permission.protocol.ReqPermissionInfo.RespPermissionInfo;
import work.metanet.api.permission.protocol.ReqPermissionList;
import work.metanet.api.permission.protocol.ReqPermissionList.RespPermissionList;
import work.metanet.api.permission.protocol.ReqRemovePermission;
import work.metanet.api.permission.protocol.ReqSavePermission;
import work.metanet.api.permission.protocol.ReqUpdPermissionParent;
import work.metanet.api.permission.vo.MenuVo;
import work.metanet.model.Permission;
import work.metanet.server.dao.PermissionMapper;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import io.seata.spring.annotation.GlobalTransactional;

@DubboService
public class PermissionService implements IPermissionService{

	@Autowired
	private PermissionMapper permissionMapper;
	
	/**
	 * @Description: 权限列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public List<RespPermissionList> permissionList(ReqPermissionList req) throws Exception {
		return permissionMapper.permissionList(BeanUtil.beanToMap(req));
	}

	/**
	 * @Description: 权限信息
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespPermissionInfo permissionInfo(ReqPermissionInfo req) throws Exception {
		return permissionMapper.permissionInfo(BeanUtil.beanToMap(req));
	}

	
	/**
	 * @Description: 保存权限
	 * @Author wanbo
	 * @DateTime 2020/06/16
	 */
	@Override
	public void savePermission(ReqSavePermission req) throws Exception {
		 Permission permission = BeanUtil.copyProperties(req, Permission.class);
		if(StringUtils.isNotBlank(req.getPermissionId())) {
			permissionMapper.updateByPrimaryKeySelective(permission);
		}else {
			permission.setPermissionId(IdUtil.fastSimpleUUID());
			permissionMapper.insertSelective(permission);
		}
	}
	
	/**
	 * @Description: 删除权限
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@GlobalTransactional
	@Override
	public void removePermission(List<ReqRemovePermission> req) throws Exception {
		permissionMapper.removePermission(req);
		for (ReqRemovePermission permission : req) {
			permissionMapper.removePermissionRole(permission.getPermissionId());
		}
	}
	
	@Override
	public void updPermissionParent(List<ReqUpdPermissionParent> req) throws Exception {
		for (ReqUpdPermissionParent o : req) {
			if(StringUtils.isEmpty(o.getParentId())) {
				o.setParentId("");
			}
			permissionMapper.updPermissionParent(o.getPermissionId(), o.getParentId());			
		}
	}
	
	public List<Map<String, Object>> permissionTree(String roleId) throws Exception{
		return permissionMapper.permissionTree(roleId);
	}
	
	@Override
	public List<MenuVo> adminMenuList(String adminId) throws Exception {
		return permissionMapper.adminMenuList(adminId);
	}
	
	@Override
	public String adminPermissionApis(String adminId) throws Exception {
		return permissionMapper.adminPermissionApis(adminId);
	}
	
	@Override
	public String adminPermissionTags(String adminId) throws Exception {
		return permissionMapper.adminPermissionTags(adminId);
	}
	
}
