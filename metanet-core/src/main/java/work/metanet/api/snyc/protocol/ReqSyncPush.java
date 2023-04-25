package work.metanet.api.snyc.protocol;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("请求-同步推送")
@Data
public class ReqSyncPush implements Serializable{
	
	private static final long serialVersionUID = 204554491311794312L;
	
	@ApiModelProperty(value = "同步模块-LEARN_DIR/TAKE_READ/TAKE_READ_DETAIL", required = true)
	@Pattern(regexp = "^(LEARN_DIR|TAKE_READ|TAKE_READ_DETAIL)$", message = "同步模块格式错误")
	private String model;
	
	@ApiModelProperty(value = "同步推送数据", required = true)
	@NotEmpty(message = "同步推送数据不能为空")
	private List<ReqSyncPushData> syncPushData;
	
	@ApiModel("请求-同步推送数据")
	@Data
	public static class ReqSyncPushData implements Serializable{
		
		private static final long serialVersionUID = 5911218273266531965L;

		@ApiModelProperty(value = "同步操作类型-SAVE/REMOVE", required = true)
		@Pattern(regexp = "^(SAVE|REMOVE)$", message = "操作类型格式错误")
		private String operType;
		
		@ApiModelProperty(value = "模块数据", required = true)
		@NotBlank(message = "模块不能为空")
		private Map<String, Object> jsonData;
	}
	
	
}
