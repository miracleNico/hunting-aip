package work.metanet.api.model.protocol;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@Data
public class ReqBrandModelList extends ReqModelList implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	
	//@NotBlank(message = "品牌id不能为空")
	private String brand;
	
	@Override
	public ReqModelList setBrand(String brand) {
		this.brand = brand;
		return super.setBrand(brand);
	}
    
    

}
