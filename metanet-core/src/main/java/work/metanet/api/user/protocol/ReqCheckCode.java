package work.metanet.api.user.protocol;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("请求-登录")
@Data
public class ReqCheckCode implements Serializable{
	
	private static final long serialVersionUID = -3851499588497776595L;

	@ApiModelProperty(value = "手机号码", required = true)
	@Pattern(regexp = "^1[345789]\\d{9}$", message = "手机号码错误")
	private String phone;
	
	@ApiModelProperty(value = "验证码", required = true)
	private String code;
	
	@ApiModelProperty(value = "验证码类型{1登录/2注册/3修改密码/4销户/5警告/6解锁}", required = true)
	@Range(min = 1, max = 6,message = "短信类型错误")
	private Integer smsType;
	
}
