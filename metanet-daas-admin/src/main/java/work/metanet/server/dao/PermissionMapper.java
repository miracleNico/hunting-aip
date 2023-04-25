package work.metanet.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import work.metanet.api.permission.protocol.ReqPermissionInfo.RespPermissionInfo;
import work.metanet.api.permission.protocol.ReqPermissionList.RespPermissionList;
import work.metanet.api.permission.vo.MenuVo;
import work.metanet.api.permission.protocol.ReqRemovePermission;
import work.metanet.model.Permission;

import tk.mybatis.mapper.common.BaseMapper;

public interface PermissionMapper extends BaseMapper<Permission>{
	
	List<Map<String, Object>> permissionTree(String roleId);
	
	/**
	 * @Description: 获取唯一
	 * @Author wanbo
	 * @DateTime 2020/06/16
	 */
	Permission permissionOnly(Map<String, String> map);
	
	/**
	 * @Description: 权限详情
	 * @Author wanbo
	 * @DateTime 2019/11/26
	 */
	RespPermissionInfo permissionInfo(Map<String, Object> map);
	
	/**
	 * @Description: 权限列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	List<RespPermissionList> permissionList(Map<String, Object> map);
	
	/**
	 * @Description: 删除权限
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	int removePermission(@Param("list")List<ReqRemovePermission> list);
	
	@Delete("delete from t_auth_role_permission where permission_id=#{permissionId}")
	int removePermissionRole(@Param("permissionId")String permissionId);
	
	/**
	 * @Description: 修改父级节点
	 * @Author wanbo
	 * @DateTime 2020/02/27
	 */
	@Update("update t_auth_permission set parent_id=#{parentId} where permission_id=#{permissionId}")
	int updPermissionParent(@Param("permissionId")String permissionId,@Param("parentId")String parentId);
	
	String adminPermissionApis(@Param("adminId")String adminId);
	
	String adminPermissionTags(@Param("adminId")String adminId);
	
	List<MenuVo> adminMenuList(@Param("adminId")String adminId);
}
