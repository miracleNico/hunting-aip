package work.metanet.api.user.protocol;

import java.io.Serializable;

import work.metanet.model.UserFromThird;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("请求-第三方用户同步")
public class ReqSyncUserFromThird extends UserFromThird implements Serializable{
	private static final long serialVersionUID = 255311965475107780L;
	
	@ApiModel("响应-同步结果")
	@Data
	public static class RespSyncUserFromThird implements Serializable {
		private static final long serialVersionUID = 3274483106047680026L;

		@ApiModelProperty(value = "第三方应用系统ID", example = "f1e247a486ef4c6ebc34cba4f775e924")
		private String appID;

		@ApiModelProperty(value = "用户ID", example = "f1e247a486ef4c6ebc34cba4f775e924")
		private String userId;
		
	    @ApiModelProperty("用户名")
	    private String username;

	    @ApiModelProperty("昵称")
	    private String nickName;
	    
	    @ApiModelProperty("电话号码")
	    private String phone;
	    
	    @ApiModelProperty(value = "年龄", example = "10")
	    private Integer age;
	    
		@ApiModelProperty(value = "左眼信息ID", example = "f1e247a486ef4c6ebc34cba4f775e924")
		private String leftEyeID;
		
		@ApiModelProperty(value = "右眼信息ID", example = "f1e247a486ef4c6ebc34cba4f775e924")
		private String rightEyeID;
	}
}
