package work.metanet.api.visionContent.protocol;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class ReqRemoveVisionContent implements Serializable{
	
	private static final long serialVersionUID = 72288590559616141L;
	
	@NotBlank(message = "内容不能为空")
	@ApiModelProperty(required = true , value = "内容Id")
	private String contentId;

	@Data
	@Accessors
	public static class RespRemoveVisionContent implements Serializable{

		@ApiModelProperty(value = "内容Id")
		private String contentId;
	}
}
