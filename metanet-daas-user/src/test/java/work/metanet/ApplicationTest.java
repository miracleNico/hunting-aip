package work.metanet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import work.metanet.api.user.IUserService;
import work.metanet.constant.ConstSmsRequestType;
import work.metanet.constant.Constant;
import work.metanet.model.User;
import work.metanet.server.dao.UserMapper;

import work.metanet.util.SmsUtil;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;

@SpringBootTest
class ApplicationTest {

	@Autowired SmsUtil SmsUtil;
	@Autowired IUserService userService;
	@Autowired UserMapper userMapper;

	@Autowired
	private Constant constant;
	
	@Test
	void testConst() {
		System.out.println(constant.getLearningMinutes_convert_score());
		System.out.println(constant.getDay_max_convert_score());
	}
	

	

	
	@Test
	void sendSms() {
		SmsUtil.sendSms(null,ConstSmsRequestType.REGISTER, "17603010900", "1234");
	}
	
	@Test
	void userInfo() throws Exception {
		System.out.println(userService.userInfo("14762874de024c6eb990fed253a2db4e"));
	}
	
	@Test
	void saveUser() {
		User user = new User();
		user.setUserId(IdUtil.fastSimpleUUID());
		user.setUsername("aaa");
		user.setPassword(SecureUtil.md5("123456"));
		user.setPhone("17300001111");
		user.setNickName("sky");
		userMapper.insertSelective(user);
	}

}
