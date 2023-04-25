package work.metanet.api.user.protocol;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("请求-销户")
@Data
public class ReqAccountCancel implements Serializable{
	
	private static final long serialVersionUID = -3851499588497776595L;
	
	@ApiModelProperty(value = "验证码", required = true)
	private String code;
	
}
