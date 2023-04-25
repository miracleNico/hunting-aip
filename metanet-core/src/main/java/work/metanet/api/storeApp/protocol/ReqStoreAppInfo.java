package work.metanet.api.storeApp.protocol;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import work.metanet.api.storeApp.protocol.ReqStoreAppScopeInfo.RespStoreAppScopeInfo;

import lombok.Data;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@Data
public class ReqStoreAppInfo implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	
	private String storeAppId;
    
    @Accessors(chain = true)
    @Data
    public static class RespStoreAppInfo implements Serializable{
    	
    	private static final long serialVersionUID = -4291721599975198242L;
    	private String channelId;
    	private String channelName;
    	private String storeAppId;
    	private String appName;
    	private String icon;
    	private String packageName;
    	private String callClass;
    	private BigDecimal fileSize;
    	private String versionName;
    	private String versionCode;
    	private String appScope;
    	private String appScopeName;
    	private Integer downloadNumber;
    	private String releaseTime;
    	private String developer;
    	private String url;
    	private String instruction;
    	private String md5;
    	private String images;
    	private Boolean enable;
        private String remark;
        private String createTime;
        private String updateTime;
    	private List<RespStoreAppScopeInfo> storeAppScopeInfoList;
    	
    }
    

}
