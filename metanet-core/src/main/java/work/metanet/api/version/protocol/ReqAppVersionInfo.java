package work.metanet.api.version.protocol;

import java.io.Serializable;

import work.metanet.api.version.protocol.ReqAppVersionList.RespAppVersionList;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@Data
public class ReqAppVersionInfo implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	
	private String versionId;
    
	@EqualsAndHashCode(callSuper=true)
    @Accessors(chain = true)
    @Data
    public static class RespAppVersionInfo extends RespAppVersionList implements Serializable{
    	
    	private static final long serialVersionUID = -4291721599975198242L;
    	private String channelId;
    	
    	
    }
    

}
