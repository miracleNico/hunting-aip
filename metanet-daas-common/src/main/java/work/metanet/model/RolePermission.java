package work.metanet.model;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "t_auth_role_permission")
public class RolePermission implements Serializable {
	
	private static final long serialVersionUID = -5190101949013404548L;
	
	@Id
	private String rolePermissionId;
	
    private String roleId;
	
    private String permissionId;
	
}
