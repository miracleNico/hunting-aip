package work.metanet.api.permission.protocol;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ReqRemovePermission implements Serializable{
	
	private static final long serialVersionUID = 8698493709431295018L;
	@NotBlank(message = "id不能为空")
	private String permissionId;
	
}
