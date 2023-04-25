package work.metanet.api.feedback.protocol;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@Data
public class ReqFeedbackInfo implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	private String feedbackId;
    
    @Accessors(chain = true)
    @Data
    public static class RespFeedbackInfo implements Serializable{
    	
    	private static final long serialVersionUID = -4291721599975198242L;
    	private String feedbackId;
        private String packageName;
        private String versionCode;
        private String content;
        private String qq;
        private String vx;
        private String tel;
        private String feedbackOptionContent1;
        private String feedbackOptionContent2;
        private String uuid;
        private String mac;
        private String brandName;
        private String modelName;
        private String processStatus;
        private String remark;
        private String createTime;
    	
    }
    
}
