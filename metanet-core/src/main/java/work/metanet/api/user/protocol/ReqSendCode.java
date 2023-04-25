package work.metanet.api.user.protocol;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("请求-验证码")
@Data
public class ReqSendCode implements Serializable{
	
	private static final long serialVersionUID = 3308160984553751868L;
	
	@ApiModelProperty(value = "手机号码", required = true)
	@Pattern(regexp = "^1[345789]\\d{9}$", message = "手机号码错误")
	private String phone;
	
	@ApiModelProperty(value = "验证码类型{1登录/2注册/3修改密码/4销户/5警告/6解锁}", required = true)
	@Range(min = 1, max = 6,message = "短信类型错误")
	private Integer smsType;
	
	
	@ApiModel(value = "响应-验证码")
	@Data
	public static class RespSendCode implements Serializable{
		
		private static final long serialVersionUID = -5255740365569977577L;
		
		@ApiModelProperty("验证码")
		private String code;
		
	}

	
}
