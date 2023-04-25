package work.metanet.api.storeApp.protocol;

import java.io.Serializable;

import work.metanet.api.storeApp.protocol.ReqStoreAppInfo.RespStoreAppInfo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Accessors(chain = true)
public class ReqSaveStoreApp extends RespStoreAppInfo implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	
    
	@EqualsAndHashCode(callSuper=true)
    @Accessors(chain = true)
    @Data
    public static class RespSaveStoreApp extends RespStoreAppInfo implements Serializable{
    	private static final long serialVersionUID = -4291721599975198242L;
    }
    

}
