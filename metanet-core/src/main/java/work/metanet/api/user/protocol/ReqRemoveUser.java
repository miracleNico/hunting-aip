package work.metanet.api.user.protocol;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ReqRemoveUser implements Serializable{
	
	private static final long serialVersionUID = 72288590559616141L;
	
	@NotBlank(message = "用户id不能为空")
	private String userId;

}
