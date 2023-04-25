package work.metanet.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import work.metanet.api.admin.protocol.ReqAdminInfo.RespAdminInfo;
import work.metanet.api.admin.protocol.ReqAdminList.RespAdminList;
import work.metanet.api.admin.protocol.ReqRemoveAdmin;
import work.metanet.model.Admin;
import work.metanet.server.vo.AdminVo;

import tk.mybatis.mapper.common.BaseMapper;

public interface AdminMapper extends BaseMapper<Admin>{
	
	/**
	 * @Description: 获取唯一
	 * @Author wanbo
	 * @DateTime 2020/06/16
	 */
	Admin adminOnly(Map<String, String> map);
	
	@Select("select * from t_admin where status=true and username=#{username} and password=#{password}")
	AdminVo getAdmin(Map<String, Object> map);
	
	//List<String> getAdminIdByAdmin(String roleId);
	
	/**
	 * @Description: 管理员详情
	 * @Author wanbo
	 * @DateTime 2019/11/26
	 */
	RespAdminInfo adminInfo(Map<String, Object> map);
	
	/**
	 * @Description: 管理员列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	List<RespAdminList> adminList(Map<String, Object> map);
	
	/**
	 * @Description: 删除管理员
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	int removeAdmin(@Param("list")List<ReqRemoveAdmin> list);
	
}
