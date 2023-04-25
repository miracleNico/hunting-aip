package work.metanet.api.feedback.protocol;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

@ApiModel("请求-获取问题反馈选项")
@Accessors(chain = true)
@Data
public class ReqGetFeedbackOption implements Serializable{

	private static final long serialVersionUID = 5448598344020657643L;
	
	@ApiModel("响应-获取问题反馈选项")
	@Data
	public static class RespGetFeedbackOption implements Serializable{
		
		private static final long serialVersionUID = -6035035594153073330L;
		private String feedbackOptionId;
		private String feedbackOptionContent;
	    private String parentId;
	    private List<RespGetFeedbackOption> children;
		
	}

}
