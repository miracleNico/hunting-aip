package work.metanet.api.user.protocol;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("请求-修改密码")
@Data
public class ReqUpdPassword implements Serializable{
	
	private static final long serialVersionUID = -3851499588497776595L;
	
	@ApiModelProperty(value = "原密码", required = true)
	@Pattern(regexp = "^\\w{4,16}$", message = "原密码格式错误")
	private String password;
	
	@ApiModelProperty(value = "新密码", required = true)
	@Pattern(regexp = "^\\w{4,16}$", message = "新密码格式错误")
	private String newPassword;
	
}
