package work.metanet.api.user.protocol;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("请求-重置密码")
@Data
public class ReqResetPassword implements Serializable{
	
	private static final long serialVersionUID = -3851499588497776595L;
	
	@ApiModelProperty(value = "手机号码", required = true)
	@Pattern(regexp = "^1[345789]\\d{9}$", message = "手机号码错误")
	private String phone;
	
	@ApiModelProperty(value = "验证码", required = true)
	private String code;
	
	@ApiModelProperty(value = "新密码", required = true)
	@Pattern(regexp = "^\\w{5,16}$", message = "新密码格式错误")
	private String newPassword;
	
}
