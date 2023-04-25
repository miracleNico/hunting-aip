package work.metanet.api.user.protocol;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("请求-注册")
@Data
public class ReqRegister implements Serializable{
	
	private static final long serialVersionUID = -2599669578391843458L;
	
	@ApiModelProperty(value = "昵称")
	private String nickName;
	
	@ApiModelProperty(value = "用户名", required = true)
	@Pattern(regexp = "^[a-z0-9_-]{3,16}$", message = "用户名格式错误")
	private String username;
	
	@ApiModelProperty(value = "密码", required = true)
	@Pattern(regexp = "^\\w{5,16}$", message = "密码格式错误")
	private String password;
	
	@ApiModelProperty(value = "手机号码", required = true)
	@Pattern(regexp = "^1[345789]\\d{9}$", message = "手机号码错误")
	private String phone;
	

}
