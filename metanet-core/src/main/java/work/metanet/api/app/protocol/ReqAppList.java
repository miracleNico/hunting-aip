package work.metanet.api.app.protocol;

import java.io.Serializable;

import work.metanet.base.Paging;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
@Data
public class ReqAppList extends Paging implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	
	//模糊定位(like id|name)
	private String app;
	private String channelId;
	private String appType;
	private String authType;
	private String packageName;
	private String instruction;
    private String remark;
    private String startTime;
	private String endTime;
	
    @Accessors(chain = true)
    @Data
    public static class RespAppList implements Serializable{
    	
    	private static final long serialVersionUID = -4291721599975198242L;
    	private String appId;
    	private String channelId;
    	private String channelName;
    	private String appName;
    	private String appType;
    	private String authType;
    	private String packageName;
    	private String appTypeName;
    	private String authTypeName;
    	private Boolean enableSn;
    	private String instruction;
    	private String appKey;
        private String appSecret;
        private String remark;
        private String createTime;
        private String updateTime;
    }
    

}
