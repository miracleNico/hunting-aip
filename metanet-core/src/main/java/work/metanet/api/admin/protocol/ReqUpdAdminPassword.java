package work.metanet.api.admin.protocol;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ReqUpdAdminPassword implements Serializable{
	
	private static final long serialVersionUID = -188980429342265134L;
	@NotBlank(message = "旧密码不能为空")
	private String oldPassword;
	@NotBlank(message = "新密码不能为空")
	private String newPassword;
	
}
