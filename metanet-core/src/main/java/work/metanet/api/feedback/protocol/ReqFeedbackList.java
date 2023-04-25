package work.metanet.api.feedback.protocol;

import java.io.Serializable;

import work.metanet.api.feedback.protocol.ReqFeedbackInfo.RespFeedbackInfo;
import work.metanet.base.Paging;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
@Data
public class ReqFeedbackList extends Paging implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	private String channelId;
	private String processStatus;
    private String remark;
    private String startTime;
	private String endTime;
	
	@EqualsAndHashCode(callSuper=true)
    @Accessors(chain = true)
    @Data
    public static class RespFeedbackList extends RespFeedbackInfo implements Serializable{
    	private static final long serialVersionUID = -4291721599975198242L;
    	private String appName;
    }
    

}
