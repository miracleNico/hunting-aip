package work.metanet.api.visionContent.protocol;

import work.metanet.model.VisionContentBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Accessors(chain = true)
@Data
public class ReqUpdateVisionContent extends VisionContentBase implements Serializable{
	@NotBlank(message = "ID 不能为空")
	@ApiModelProperty(example = "a7e439a7ee8b4c0a93f993423b8ea7af",required = true ,value = "内容ID")
	private String contentId;
	private static final long serialVersionUID = 3456788965L;


	@Data
	@Accessors(chain = true)
	public static class RespUpdateVisionContent implements Serializable{

		@ApiModelProperty(value = "内容ID")
		private String contentId;
	}

}
