package work.metanet.api.storeApp.protocol;

import java.io.Serializable;

import work.metanet.api.storeApp.protocol.ReqStoreAppInfo.RespStoreAppInfo;
import work.metanet.base.Paging;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
@Data
public class ReqStoreAppList extends Paging implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	private String channelId;
	private String appName;
	private String appId;
	private String packageName;
	private Boolean enable;
	private String startTime;
	private String endTime;
	private String remark;
	
	@EqualsAndHashCode(callSuper=true)
    @Accessors(chain = true)
    @Data
    public static class RespStoreAppList extends RespStoreAppInfo implements Serializable{
    	private static final long serialVersionUID = -4291721599975198242L;
    }
    

}
