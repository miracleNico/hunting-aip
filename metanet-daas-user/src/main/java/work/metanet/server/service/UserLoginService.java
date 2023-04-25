package work.metanet.server.service;


import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import work.metanet.api.user.IUserLoginService;
import work.metanet.model.UserLogin;
import work.metanet.server.dao.UserLoginMapper;

import cn.hutool.core.util.IdUtil;

@DubboService
public class UserLoginService implements IUserLoginService{
	
	@Autowired
	private UserLoginMapper userLoginMapper;
	
	@Async
	@Override
	public void loginRecord(String userId, String deviceId, String versionId) {
		UserLogin userLogin = new UserLogin()
				.setUserLoginId(IdUtil.fastSimpleUUID())
				.setUserId(userId)
				.setDeviceId(deviceId)
				.setVersionId(versionId);
		userLoginMapper.insertSelective(userLogin);
	}
	
	
}
