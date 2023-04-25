package work.metanet.api.admin;

import java.util.List;

import work.metanet.api.admin.protocol.ReqAdminInfo;
import work.metanet.api.admin.protocol.ReqAdminInfo.RespAdminInfo;
import work.metanet.api.admin.protocol.ReqAdminList;
import work.metanet.api.admin.protocol.ReqAdminList.RespAdminList;
import work.metanet.api.admin.protocol.ReqAdminLogin;
import work.metanet.api.admin.protocol.ReqAdminLogin.RespAdminLogin;
import work.metanet.api.admin.protocol.ReqRemoveAdmin;
import work.metanet.api.admin.protocol.ReqSaveAdmin;
import work.metanet.api.admin.protocol.ReqUpdAdminPassword;
import work.metanet.base.RespPaging;

public interface IAdminService {
	
	void cacheSession(String adminId,String sessionId) throws Exception;
	
	String cacheSession(String adminId) throws Exception;

	RespAdminLogin login(ReqAdminLogin req)throws Exception; 
	
	void updAdminPassword(String adminId,ReqUpdAdminPassword req) throws Exception;
	
	/**
	 * @Description: 管理员列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespPaging<RespAdminList> adminList(ReqAdminList req) throws Exception;
	
	/**
	 * @Description: 管理员详情
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespAdminInfo adminInfo(ReqAdminInfo req) throws Exception;
	
	/**
	 * @Description: 保存管理员
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void saveAdmin(ReqSaveAdmin req) throws Exception;
	
	/**
	 * @Description: 删除管理员
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void removeAdmin(List<ReqRemoveAdmin> req) throws Exception;
	
	/**
	 * @Description: 重置密码
	 * @Author wanbo
	 * @DateTime 2020/07/03
	 */
	void resetAdminPassword(String adminId) throws Exception;
	
}
