package work.metanet.api.brand.protocol;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ReqRemoveBrand implements Serializable{
	
	private static final long serialVersionUID = 72288590559616141L;
	
	@NotBlank(message = "品牌不能为空")
	private String brandId;

}
