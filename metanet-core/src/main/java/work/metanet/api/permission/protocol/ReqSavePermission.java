package work.metanet.api.permission.protocol;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@Data
public class ReqSavePermission implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	private String permissionId;
	private String permissionName;
	private String permissionType;
	private String permissionTag;
	private String parentId;
    private String apis;
    private Integer sort;
    private String icon;
	private String remake;
	
}
