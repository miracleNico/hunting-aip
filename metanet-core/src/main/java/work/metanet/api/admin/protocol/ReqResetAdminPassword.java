package work.metanet.api.admin.protocol;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@Data
public class ReqResetAdminPassword implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	@NotBlank(message = "管理员id不能为空")
	private String adminId;
	
}
