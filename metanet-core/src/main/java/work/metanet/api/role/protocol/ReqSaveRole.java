package work.metanet.api.role.protocol;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@Data
public class ReqSaveRole implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	private String roleId;
	private String roleName;
	private String roleType;
	private String remark;
	private List<String> permissionList;
	
}
