package work.metanet.api.user.protocol;

import java.io.Serializable;

import org.hibernate.validator.constraints.Range;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("请求-登录")
@Data
public class ReqLogin implements Serializable{
	
	private static final long serialVersionUID = -3851499588497776595L;
	
	@ApiModelProperty(value = "用户名/手机号码", required = true)
	private String username;
	
	@ApiModelProperty(value = "密码/验证码", required = true)
	private String password;
	
	@ApiModelProperty(value = "登录类型{1手机验证码登录/2用户名密码登录}", required = true, allowableValues = "1, 2")
	@Range(min = 1, max = 2,message = "登录类型错误")
	private Integer loginType;
	
	
	
	@ApiModel("响应-登录")
	@Data
	public static class RespLogin implements Serializable{
		
		private static final long serialVersionUID = 595303890147853161L;
		
		@ApiModelProperty("token")
		private String token;
		
		@ApiModelProperty("用户id")
		private String userId;
		
		@ApiModelProperty(value = "用户名")
		private String username;
		
		@ApiModelProperty(value = "昵称")
		private String nickName;
		
		@ApiModelProperty(value = "生日")
		private String birthday;
		
		@ApiModelProperty(value = "省")
		private String province;
		
		@ApiModelProperty(value = "市")
		private String city;
		
		@ApiModelProperty(value = "邮箱")
		private String email;
		
		@ApiModelProperty(value = "性别{0女/1男/2保密}")
		private String gender;
		
		@ApiModelProperty(value = "手机号码")
		private String phone;
		
		@ApiModelProperty(value = "头像")
		private String headUrl;
		
	}

	
}
