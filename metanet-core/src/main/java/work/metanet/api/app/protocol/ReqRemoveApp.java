package work.metanet.api.app.protocol;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ReqRemoveApp implements Serializable{
	
	private static final long serialVersionUID = 1055722802402768004L;

	@NotBlank(message = "产品id不能为空")
	private String appId;
	
}
