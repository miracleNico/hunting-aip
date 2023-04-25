package work.metanet.api.permission.protocol;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReqUpdPermissionParent implements Serializable{
	
	private static final long serialVersionUID = -8542855042034048825L;
	private String permissionId;
	private String parentId;
	
}
