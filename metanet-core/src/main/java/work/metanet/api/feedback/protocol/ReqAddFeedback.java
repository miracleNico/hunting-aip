package work.metanet.api.feedback.protocol;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@ApiModel("请求-新增问题反馈")
@Accessors(chain = true)
@Data
public class ReqAddFeedback implements Serializable{

	private static final long serialVersionUID = 5448598344020657643L;
	
	@ApiModelProperty(value = "反馈内容", required = true)
	@NotBlank(message = "反馈内容不能为空")
	private String content;
	private String qq;
    private String vx;
    private String tel;
    private String feedbackOptionContent1;
    private String feedbackOptionContent2;
    @ApiModelProperty(value = "uuid")
    private String uuid;
    @ApiModelProperty(value = "mac地址")
    private String mac;
    @ApiModelProperty(value = "品牌")
    private String brandName;
    @ApiModelProperty(value = "型号")
    private String modelName;

}
