package work.metanet.api.storeApp.protocol;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class ReqStoreAppScopeInfo implements Serializable{
	
	private static final long serialVersionUID = -4291721599975198242L;
	private String storeAppId;
	
	@Accessors(chain = true)
	@Data
	public static class RespStoreAppScopeInfo implements Serializable{
		private static final long serialVersionUID = -4291721599975198242L;
		private String storeAppScopeId;
		private String storeAppId;
		private String appId;
		private String appName;
	}
	
}