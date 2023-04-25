package work.metanet.api.vision.protocol;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import work.metanet.model.EyeBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
/**
 * @author Edison F.
 * @Description 请求-眼睛详情
 * @DateTime 2021/04/20
 */
@ApiModel("请求-眼睛详情")
@Accessors(chain = true)
@Data
public class ReqEyeInfo implements Serializable {
	private static final long serialVersionUID = -2960867261125552554L;

	@ApiModelProperty(value = "眼睛id,UUID", example = "48a66898a83c11eb943f00ff71c9db07")
	private String eyeId;
	
	@ApiModelProperty(value = "用户id,UUID", example = "e7e6dfb0a83911eb943f00ff71c9db07")
	private String userId;	
    
    @ApiModel("响应-眼睛详情")
    public static class RespEyeInfo extends EyeBase implements Serializable{

		private static final long serialVersionUID = -8010278430535822031L;
    }
}
