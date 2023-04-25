package work.metanet.api.user.protocol;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import work.metanet.api.user.protocol.ReqLogin.RespLogin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ApiModel("请求-登录与注册")
@Data
public class ReqLoginSuper implements Serializable{
	
	private static final long serialVersionUID = 5806086286953594810L;
	
	@ApiModelProperty(value = "手机号码", required = true)
	@Pattern(regexp = "^1[345789]\\d{9}$", message = "手机号码错误")
	private String phone;
	
	@ApiModelProperty(value = "验证码", required = true)
	private String code;
	
	@ApiModelProperty(value = "密码")
	private String password;
	
	@ApiModelProperty(value = "设备id")
	private String deviceId;
	
	
	@ApiModel("响应-登录与注册")
	@EqualsAndHashCode(callSuper=true)
	@Data
	public static class RespLoginSuper extends RespLogin implements Serializable{
		
		private static final long serialVersionUID = 3274483106047680026L;
		
	}
	
	
}
