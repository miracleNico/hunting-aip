package work.metanet.api.vision.protocol;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
/**
 * @author Edison F.
 * @Description 请求-删除-眼睛
 * @DateTime 2021/04/20
 */
@Accessors(chain = true)
@Data
public class ReqRemoveEye implements Serializable {

	private static final long serialVersionUID = 2289721187389651939L;
	
	@ApiModelProperty(value = "眼睛id,UUID", required = true, example = "d62c4636ada84022be827c63d3c89fed")
	@NotBlank(message = "eyeId不能为空")
    private String eyeId;
}
