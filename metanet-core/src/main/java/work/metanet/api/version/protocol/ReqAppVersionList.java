package work.metanet.api.version.protocol;

import java.io.Serializable;
import java.math.BigDecimal;

import work.metanet.base.Paging;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
@Data
public class ReqAppVersionList extends Paging implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	
	//模糊定位(like code|name)
	private String version;
	private String app;
	private String channelId;
	private String versionType;
	private String startTime;
	private String endTime;
	private String instruction;
	private String remark;
    
    @Accessors(chain = true)
    @Data
    public static class RespAppVersionList implements Serializable{
    	
    	private static final long serialVersionUID = -4291721599975198242L;
    	
    	private String versionId;
    	private String versionCode;
    	private String versionName;
    	private String versionType;
    	private String versionTypeName;
    	private BigDecimal fileSize;
    	private String md5;
        private String url;
        private String urlType;
    	private String appId;
    	private String appName;
    	private String instruction;
        private String remark;
        private String createTime;
    	private String updateTime;
    	
    }
    

}
