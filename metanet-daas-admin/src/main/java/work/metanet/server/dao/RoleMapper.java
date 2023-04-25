package work.metanet.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import work.metanet.api.role.protocol.ReqRemoveRole;
import work.metanet.api.role.protocol.ReqRoleInfo.RespRoleInfo;
import work.metanet.api.role.protocol.ReqRoleList.RespRoleList;
import work.metanet.model.Role;

import tk.mybatis.mapper.common.BaseMapper;

public interface RoleMapper extends BaseMapper<Role>{
	
	/**
	 * @Description: 获取唯一
	 * @Author wanbo
	 * @DateTime 2020/06/16
	 */
	Role roleOnly(Map<String, String> map);
	
	/**
	 * @Description: 角色详情
	 * @Author wanbo
	 * @DateTime 2019/11/26
	 */
	RespRoleInfo roleInfo(Map<String, Object> map);
	
	/**
	 * @Description: 角色列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	List<RespRoleList> roleList(Map<String, Object> map);
	
	/**
	 * @Description: 删除角色
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	int removeRole(@Param("list")List<ReqRemoveRole> list);
	
	/**
	 * @Description: 删除角色权限
	 * @Author wanbo
	 * @DateTime 2020/06/18
	 */
	@Delete("delete from t_auth_role_permission where role_id=#{roleId}")
	int removeRolePermission(@Param("roleId")String roleId);
	
	/**
	 * @Description: 配置角色权限
	 * @Author wanbo
	 * @DateTime 2020/06/18
	 */
	int addRolePermission(@Param("roleId")String roleId,@Param("permissionList")List<String> permissionList);
	
	/**
	 * @Description: 配置管理员角色
	 * @Author wanbo
	 * @DateTime 2020/06/18
	 */
	int addAdminRole(@Param("adminId")String adminId,@Param("roleIdList")List<String> roleIdList);
	
	/**
	 * @Description: 获取管理员角色
	 * @Author wanbo
	 * @DateTime 2020/07/03
	 */
	@Select("select role_id from t_auth_admin_role where admin_id=#{adminId}")
	List<String> getRoleIdListByAdminId(String adminId);
	

	/**
	 * @Description: 删除管理员角色关联
	 * @Author wanbo
	 * @DateTime 2020/06/18
	 */
	@Delete("delete from t_auth_admin_role where admin_id=#{adminId}")
	int removeAdminRole(@Param("adminId")String adminId);
	
	@Delete("delete from t_auth_admin_role where role_id=#{roleId}")
	int removeRoleAdmin(@Param("roleId")String roleId);
}
