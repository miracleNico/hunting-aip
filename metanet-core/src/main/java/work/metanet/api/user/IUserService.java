package work.metanet.api.user;

import java.util.List;

import work.metanet.api.user.protocol.ReqAccountCancel;
import work.metanet.api.user.protocol.ReqCheckCode;
import work.metanet.api.user.protocol.ReqLogin.RespLogin;
import work.metanet.api.user.protocol.ReqLoginSuper;
import work.metanet.api.user.protocol.ReqRemoveUser;
import work.metanet.api.user.protocol.ReqResetPassword;
import work.metanet.api.user.protocol.ReqSendCode;
import work.metanet.api.user.protocol.ReqSyncUserFromThird;
import work.metanet.api.user.protocol.ReqSyncUserFromThird.RespSyncUserFromThird;
import work.metanet.api.user.protocol.ReqUpdPassword;
import work.metanet.api.user.protocol.ReqUpdUser;
import work.metanet.api.user.protocol.ReqUserInfo.RespUserInfo;
import work.metanet.api.user.protocol.ReqUserList;
import work.metanet.api.user.protocol.ReqUserList.RespUserList;
import work.metanet.base.RespPaging;
import work.metanet.model.UserFromThird;

public interface IUserService {
	
	Integer userTotal(String channelId) throws Exception;
	
	String cacheUserToken(String userId) throws Exception;
	
	void cacheUserToken(String userId,String token)throws Exception;
	
	Boolean hashUserTokenKey(String userId) throws Exception;

	/**
	 * @Description: 发送手机验证码
	 * @Author wanbo
	 * @DateTime 2019/11/12
	 */
	void sendCode(String packageName,ReqSendCode requestParam) throws Exception;
	
	
	/**
	 * @Description: 确认验证码
	 * @Author wanbo
	 * @DateTime 2019/12/03
	 */
	void checkCode(String userId,ReqCheckCode requestParam) throws Exception;
	
	/**
	 * @Description: 注册
	 * @Author wanbo
	 * @DateTime 2019/11/12
	 */
	//void register(String packageName,ReqRegister requestParam) throws Exception;
	
	/**
	 * @Description: 登录
	 * @Author wanbo
	 * @DateTime 2019/11/12
	 */
	//RespLogin login(String deviceId,String packageName,String versionCode, ReqLogin requestParam) throws Exception;
	
	/**
	 * @Description: 登录与注册
	 * @Author wanbo
	 * @DateTime 2019/11/12
	 */
	RespLogin loginSuper(String deviceId,String packageName,String versionCode,ReqLoginSuper requestParam) throws Exception;
	
	/**
	 * @Description: 修改用户信息
	 * @Author wanbo
	 * @DateTime 2019/11/12
	 */
	void updUser(String userId,ReqUpdUser requestParam) throws Exception;
	
	/**
	 * @Description: 获取用户信息
	 * @Author wanbo
	 * @DateTime 2019/11/12
	 */
	RespUserInfo userInfo(String userId) throws Exception;

	/**
	 * @Description: 获取用户信息
	 * @Author wanbo
	 * @DateTime 2019/11/12
	 */
	RespUserInfo userInfoFromThird(UserFromThird uft) throws Exception;
	
	/**
	 * @Description: 修改密码
	 * @Author wanbo
	 * @DateTime 2019/11/12
	 */
	void updPassword(String userId,ReqUpdPassword requestParam) throws Exception;
	
	/**
	 * @Description: 重置密码
	 * @Author wanbo
	 * @DateTime 2019/11/17
	 */
	void resetPassword(String userId,ReqResetPassword requestParam) throws Exception;
	
	/**
	 * @Description: 用户列表
	 * @Author wanbo
	 * @DateTime 2019/11/14
	 */
	RespPaging<RespUserList> userList(ReqUserList requestParam) throws Exception;
	
	/**
	 * @Description: 删除用户
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void removeUser(List<ReqRemoveUser> req) throws Exception;
	
	/**
	 * @Description: 退出登录
	 * @Author wanbo
	 * @DateTime 2020/05/05
	 */
	void logout(String userId) throws Exception;
	
	/**
	 * @Description: 销户
	 * @Author wanbo
	 * @DateTime 2020/03/09
	 */
	void accountCancel(String userId,ReqAccountCancel req) throws Exception;
	
	
	/**
	 * @Description: 使用第三方的电话号码创建新用户
	 * @Author Edison F.
	 * @DateTime 2021/04/26
	 */
	String syncUser(String phone) throws Exception;
	
	/**
	 * @Description: 使用第三方用户信息创建新用户
	 * @Author Edison F.
	 * @DateTime 2021/04/26
	 */
	RespSyncUserFromThird syncUserMore(ReqSyncUserFromThird user) throws Exception;
	
}
