package work.metanet.api.storeApp.protocol;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ReqRemoveStoreApp implements Serializable{
	
	private static final long serialVersionUID = 72288590559616141L;
	@NotBlank(message = "商店应用id不能为空")
	private String storeAppId;

}
