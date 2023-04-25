package work.metanet.server.service;


import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.system.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import work.metanet.api.app.IAppService;
import work.metanet.api.app.vo.AppVo;
import work.metanet.api.deviceApp.IDeviceAppService;
import work.metanet.api.user.IUserLoginService;
import work.metanet.api.user.IUserService;
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

import work.metanet.api.version.IAppVersionService;
import work.metanet.api.version.vo.AppVersionVo;
import work.metanet.base.RespPaging;
import work.metanet.base.UserToken;
import work.metanet.constant.ConstCacheKey;
import work.metanet.constant.ConstSmsRequestType;
import work.metanet.constant.Constant;
import work.metanet.exception.DaasException;
import work.metanet.model.User;
import work.metanet.model.UserFromThird;
import work.metanet.server.dao.UserMapper;
import work.metanet.server.vo.UserVo;

import work.metanet.util.SmsUtil;
import work.metanet.utils.CosUtil;
import work.metanet.utils.token.TokenUtil;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;

@DubboService
public class UserService implements IUserService {
	private static Logger log = Logger.getLogger("UserService");
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private SmsUtil SmsUtil;
	@DubboReference
	private IDeviceAppService deviceAppService;
	@Autowired
	private CosUtil cosUtil;
	@DubboReference
	private IAppService appService;
	@DubboReference
	private IAppVersionService appVersionService;
	@Autowired
	private IUserLoginService userLoginService;

	@Autowired
	private Constant constant;

	@Override
	public Integer userTotal(String channelId) throws Exception {
		return userMapper.userTotal(channelId);
	}
	
	@Override
	public String cacheUserToken(String userId) throws Exception {
		return stringRedisTemplate.opsForValue().get(ConstCacheKey.USER_TOKEN.cacheKey(userId));
	}
	
	@Override
	public void cacheUserToken(String userId, String token) throws Exception {
		stringRedisTemplate.opsForValue().set(ConstCacheKey.USER_TOKEN.cacheKey(userId), token, Duration.ofSeconds(ConstCacheKey.USER_TOKEN.getExpire()));
	}
	
	@Override
	public Boolean hashUserTokenKey(String userId) throws Exception {
		String cacheKey = ConstCacheKey.USER_TOKEN.cacheKey(userId);
        return stringRedisTemplate.hasKey(cacheKey);
	}
	
	@Override
	public void sendCode(String packageName,ReqSendCode req) throws Exception {
		String validateCode = RandomUtil.randomString(constant.getRandom_base_number(), 4);
		ConstSmsRequestType smsRequestType = ConstSmsRequestType.setSmsType(req.getSmsType());
		String sign = Convert.toStr(stringRedisTemplate.opsForHash().get(ConstCacheKey.APP_SMS_SIGN.cacheKey(), packageName));
		//异步发送短信验证码
		SmsUtil.sendSms(sign, smsRequestType, req.getPhone(), validateCode);
		String cachekey = ConstCacheKey.SMS.cacheKey(smsRequestType.name(),req.getPhone());
		stringRedisTemplate.opsForValue().set(cachekey, validateCode, Duration.ofSeconds(ConstCacheKey.SMS.getExpire()));
		
		/**
		SendSmsResponse smsResponse = SmsUtil.sendSms(smsRequestType, req.getPhone(), validateCode);
		if(smsResponse==null) throw DaasException.of().setMsg("验证码发送失败");
		if("OK".equals(smsResponse.getCode())) {
			String cachekey = ConstRedisCache.SMS.cacheKey(smsRequestType.name(),req.getPhone());
			stringRedisTemplate.opsForValue().set(cachekey, validateCode, Duration.ofSeconds(ConstRedisCache.SMS.getExpire()));
		}else {
			throw DaasException.of().setMsg("验证码发送失败");
		}*/
	}
	
	
	@Override
	public void checkCode(String userId,ReqCheckCode requestParam) throws Exception {
		validationCode(userId,requestParam.getPhone(),requestParam.getCode(),ConstSmsRequestType.setSmsType(requestParam.getSmsType()));
	}
	
	
	private void validationCode(String userId,String phone,String code,ConstSmsRequestType constSmsRequestType) throws Exception{
		if(constSmsRequestType==ConstSmsRequestType.CHANGE_PASSWORD) {
			User user = userMapper.selectByPrimaryKey(userId);
			if(!user.getPhone().equals(phone))
				throw DaasException.of().setMsg("手机号码与注册时的手机号码不一致");
		}
		String cachekey = ConstCacheKey.SMS.cacheKey(constSmsRequestType.name(),phone);
		String cacheCode = stringRedisTemplate.opsForValue().get(cachekey);
		if(StringUtils.isEmpty(cacheCode)) 
			throw DaasException.of().setMsg("验证码已失效");
		if(!cacheCode.equals(code)) 
			throw DaasException.of().setMsg("验证码错误");
		stringRedisTemplate.delete(cachekey);
	}
	
	/**
	@Override
	public void register(String packageName,ReqRegister requestParam) throws Exception {
		AppVo appVo = appService.getAppByPackageName(packageName);
		User user = new User();
		user.setUserId(IdUtil.fastSimpleUUID());
		user.setAppId(appVo.getAppId());
		user.setUsername(requestParam.getUsername());
		user.setPassword(SecureUtil.md5(requestParam.getPassword()));
		user.setPhone(requestParam.getPhone());
		user.setNickName(requestParam.getNickName());
		if(StringUtils.isEmpty(user.getNickName())) {
			user.setNickName(user.getPhone().substring(user.getPhone().length()-6,user.getPhone().length()));			
		}
		userMapper.insertSelective(user);
	}*/
	
	
	
	private RespLogin login(UserVo userVo,String deviceId,String packageName,String versionCode) throws Exception{
		if(userVo==null)
			throw DaasException.of().setMsg("用户名或密码错误");
		if(!userVo.getEnableStatus())
			throw DaasException.of().setMsg("您已被锁定");
		
		AppVersionVo appVersionVo = appVersionService.getAppVersion(packageName, versionCode);
	 	String deviceAppId = deviceAppService.getDeviceAppId(deviceId, packageName);
	 	
	 	//登录记录
	 	userLoginService.loginRecord(userVo.getUserId(), deviceId, appVersionVo.getVersionId());
	 	
	 	//生成token(阶段一过期时间限制拉长,实际是需要采用短期刷新token方案)
	 	UserToken userToken = new UserToken(deviceAppId, deviceId, appVersionVo.getAppId() ,userVo.getUserId(), userVo.getUsername(),false);
	 	String token = TokenUtil.generateToken(JSONUtil.toJsonStr(userToken), 60*60*24*365L);
		
		//更新用户token
		cacheUserToken(userVo.getUserId(), token);
		
		RespLogin resp = new RespLogin();
		BeanUtil.copyProperties(userVo, resp);
		resp.setToken(token);
		resp.setHeadUrl(cosUtil.getAccessUrl(resp.getHeadUrl()));
		
		return resp;
	}
	
	/**
	@Override
	public RespLogin login(String deviceId,String packageName,String versionCode,ReqLogin requestParam) throws Exception {
		AppVo appVo = appService.getAppByPackageName(packageName);
		UserVo userVo = null;
		if(requestParam.getLoginType()==ConstLoginType.PHONE_VALIDATE_CODE_LOGIN.getVal()) {
			validationCode(null,requestParam.getUsername(),requestParam.getPassword(),ConstSmsRequestType.LOGIN);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("appId", appVo.getAppId());
			map.put("phone", requestParam.getUsername());
			userVo = userMapper.getUser(map);
			if (userVo==null) 
				throw DaasException.of().setMsg("请先注册再登录");
		}
		
		if(requestParam.getLoginType()==ConstLoginType.USERNAME_PASSWORD_LOGIN.getVal()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("appId", appVo.getAppId());
			map.put("username", requestParam.getUsername());
			map.put("password", SecureUtil.md5(requestParam.getPassword()));
			userVo = userMapper.getUser(map);
		}
		
		return login(userVo,deviceId,packageName,versionCode);
	}*/
	
	
	@Override
	public RespLogin loginSuper(String deviceId,String packageName,String versionCode,ReqLoginSuper requestParam) throws Exception {

		//短信验证码
		validationCode(null,requestParam.getPhone(),requestParam.getCode(),ConstSmsRequestType.LOGIN);
		
		AppVo appVo = appService.getAppByPackageName(packageName);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appId", appVo.getAppId());
		map.put("phone", requestParam.getPhone());
		UserVo userVo = userMapper.getUser(map);
		
		if(userVo==null) {
			if(StringUtils.isEmpty(requestParam.getPassword())) throw DaasException.of().setMsg("请输入密码");
			//注册
			userVo = new UserVo();
			userVo.setAppId(appVo.getAppId());
			userVo.setUserId(IdUtil.fastSimpleUUID());
			userVo.setUsername(requestParam.getPhone());
			userVo.setPassword(SecureUtil.md5(requestParam.getPassword()));
			userVo.setPhone(requestParam.getPhone());
			userVo.setNickName(userVo.getPhone().substring(userVo.getPhone().length()-6,userVo.getPhone().length()));
			userVo.setEnableStatus(true);
			userMapper.insertSelective(userVo);

		}
		
		//登录
		return login(userVo,deviceId,packageName,versionCode);
	}
	
	
	
	@Override 
	public void updUser(String userId,ReqUpdUser requestParam) throws Exception {
		User user = userMapper.selectByPrimaryKey(userId);
		BeanUtil.copyProperties(requestParam, user,CopyOptions.create().ignoreNullValue());
		user.setHeadUrl(cosUtil.filterUrlDomain(user.getHeadUrl()));
		userMapper.updateByPrimaryKeySelective(user);
	}



	
	
	@Override
	public RespUserInfo userInfo(String userId) throws Exception {
		User user = userMapper.selectByPrimaryKey(userId);
		if(user==null) 
			throw DaasException.of().setMsg("用户不存在");
		RespUserInfo respUserInfo = new RespUserInfo();
		BeanUtil.copyProperties(user, respUserInfo);
		respUserInfo.setHeadUrl(cosUtil.getAccessUrl(respUserInfo.getHeadUrl()));
  		return respUserInfo;
	}

	@Override
	public RespUserInfo userInfoFromThird(UserFromThird utf) throws Exception {
		RespUserInfo userInfo = userMapper.userInfoFromThird(utf);
		return userInfo;
	}


	@Override
	public void updPassword(String userId,ReqUpdPassword requestParam) throws Exception {
		User user = userMapper.selectByPrimaryKey(userId);
		
		if(!user.getPassword().equals(SecureUtil.md5(requestParam.getPassword())))
			throw DaasException.of().setMsg("原密码错误");
		user.setPassword(SecureUtil.md5(requestParam.getNewPassword()));
		userMapper.updateByPrimaryKeySelective(user);
	}
	
	
	@Override
	public void resetPassword(String userId,ReqResetPassword requestParam) throws Exception {
		validationCode(userId,requestParam.getPhone(),requestParam.getCode(),ConstSmsRequestType.CHANGE_PASSWORD);
		User user = userMapper.selectByPrimaryKey(userId);
		user.setPassword(SecureUtil.md5(requestParam.getNewPassword()));
		userMapper.updateByPrimaryKeySelective(user);
	}
	
	
	@Override 
	public RespPaging<RespUserList> userList(ReqUserList requestParam) throws Exception {
		RespPaging<RespUserList> respPaging = new RespPaging<RespUserList>();
		PageHelper.startPage(requestParam.getPageNum(), requestParam.getPageSize());
		List<RespUserList> dbUsers = userMapper.getUserList(BeanUtil.beanToMap(requestParam));
		BeanUtil.copyProperties(new PageInfo<RespUserList>(dbUsers), respPaging);
		return respPaging; 
	}
	
	/**
	 * @Description: 删除用户
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public void removeUser(List<ReqRemoveUser> req) throws Exception {
		userMapper.removeUser(req);
	}
	
	/**
	 * @Description: 退出登录
	 * @Author wanbo
	 * @DateTime 2020/05/05
	 */
	@Async
	@Override
	public void logout(String userId) throws Exception {
		stringRedisTemplate.delete(ConstCacheKey.USER_TOKEN.cacheKey(userId));
	}
	
	/**
	 * @Description: 销户
	 * @Author wanbo
	 * @DateTime 2020/03/09
	 */
	@Override
	public void accountCancel(String userId,ReqAccountCancel req) throws Exception {
		User user = userMapper.selectByPrimaryKey(userId);
		if(user==null) 
			throw DaasException.of().setMsg("查找用户失败");
		validationCode(null,user.getPhone(),req.getCode(),ConstSmsRequestType.ACCOUNT_CANCEL);
		userMapper.removeUser(CollUtil.newArrayList(new ReqRemoveUser().setUserId(userId)));
	}
	
	@Override
	public String syncUser(String phone) throws Exception {
		AppVo appVo = appService.getAppByPackageName(constant.getYmsjPackage());
		User dbUser = userMapper.selectOne(new User().setAppId(appVo.getAppId()).setPhone(phone).setStatus(true));
		if(BeanUtil.isEmpty(dbUser)) {
			dbUser = new User()
					.setUserId(IdUtil.fastSimpleUUID())
					.setAppId(appVo.getAppId())
					.setPhone(phone)
					.setUsername(phone)
					.setPassword(SecureUtil.md5("0000"))
					.setNickName(phone.substring(phone.length()-6,phone.length()))
					.setEnableStatus(true)
					;
			userMapper.insertSelective(dbUser);
		}
		return dbUser.getUserId();
	}
	
	@Override
	public RespSyncUserFromThird syncUserMore(ReqSyncUserFromThird user) throws Exception {
		String name = "第三方同步用户"; //设置默认昵称
		String tel = "13800000000"; //设置默认电话号码
		String appId = "f1e247a486ef4c6ebc34cba4f775e924"; //设置一个默认的appid
		String userId = "edison3";  //设置默认用户名
		String password = "0000"; //设置默认密码，四个0
		Integer age = 0; //设置默认年龄0
//		System.out.println("syncUserMore");
		RespSyncUserFromThird resp = new RespSyncUserFromThird();
		if(user != null) {
//			System.out.println("user != null");
			if(StringUtils.isNotBlank(user.getOwnerTel())) {

//				System.out.println("syncUserMore用户电话号码不为空："+user.getOwnerTel());

				tel = user.getOwnerTel();
			}
			if(StringUtils.isNotBlank(user.getOwnerName())) {

//				System.out.println("syncUserMore用户名称不为空："+user.getOwnerName());
				name = user.getOwnerName();
			}
			
			if(StringUtils.isNotBlank(user.getOwnerId())) {
//				System.out.println("syncUserMore用户ID为空："+user.getOwnerId());
				userId = user.getOwnerId();
			}
			age = user.getOwnerAge();
		}
		try {

			RespUserInfo respUserInfo = userInfoFromThird(user);
			if (respUserInfo != null && StringUtils.isNotBlank(respUserInfo.getUserId())) {
				Map<String, Object> map = BeanUtil.beanToMap(user);
				map.put("userId",respUserInfo.getUserId());
				userMapper.updateUserById(map);
				BeanUtil.copyProperties(userMapper.selectOne(new User().setUserId(respUserInfo.getUserId())),resp);
				return resp;
			}
			User dbUser = new User()
					.setUserId(IdUtil.fastSimpleUUID())
					.setAppId(appId)
					.setPhone(tel)
					.setUsername(userId)
					.setPassword(SecureUtil.md5(password))
					.setNickName(name)
					.setEnableStatus(true)
					.setAge(age)
					;

//			System.out.println("syncUserMore开始插入用户信息");
			if(userMapper.insertSelective(dbUser) >= 0) {
				BeanUtil.copyProperties(dbUser, resp);
			}
		}
		catch(Exception e) {
			throw DaasException.of().setMsg("同步用户信息异常:" + e.getMessage());
		}

		return resp;

	}
}
