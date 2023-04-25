package work.metanet.api.user.protocol;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("请求-修改用户信息")
@Data
public class ReqUpdUser implements Serializable{
	
	private static final long serialVersionUID = -3851499588497776595L;
	
	@ApiModelProperty(value = "昵称", required = true)
	@Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9_]+$", message = "昵称格式错误")
	private String nickName;
	
	@ApiModelProperty(value = "生日", required = false)
	@Pattern(regexp = "^\\d{4}-\\d{1,2}-\\d{1,2}$", message = "生日格式错误")
	private String birthday;
	
	@Pattern(regexp = "^\\d{6}$", message = "省格式错误")
	@ApiModelProperty(value = "省", required = false)
	private String province;
	
	@Pattern(regexp = "^\\d{6}$", message = "市格式错误")
	@ApiModelProperty(value = "市", required = false)
	private String city;
	
	@ApiModelProperty(value = "邮箱", required = false)
	@Pattern(regexp = "^[0-9a-zA-Z](\\w)+@(\\w)+(\\.)(com|cn|net|edu|com(\\.)cn)$",message = "邮箱格式错误")
	private String email;
	
	@ApiModelProperty(value = "性别{0女/1男/2保密}", required = true)
	@Pattern(regexp = "^(0|1|2)$", message = "性别格式错误")
	private String gender;
	
	@ApiModelProperty(value = "年龄", required = false)
	private Integer age;
	
	@ApiModelProperty(value = "头像", required = false)
	private String headUrl;
	
}
