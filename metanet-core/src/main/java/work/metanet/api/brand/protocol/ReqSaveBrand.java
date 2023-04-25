package work.metanet.api.brand.protocol;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@Data
public class ReqSaveBrand implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	
	private String brandId;
	private String brandName;
	private String source;
    private String remark;
	
    
	@EqualsAndHashCode(callSuper=true)
    @Accessors(chain = true)
    @Data
    public static class RespSaveBrand extends ReqSaveBrand implements Serializable{
    	
    	private static final long serialVersionUID = -4291721599975198242L;
    	
    	
    	
    }
    

}
