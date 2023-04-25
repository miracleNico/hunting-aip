package work.metanet.api.brand.protocol;

import java.io.Serializable;

import work.metanet.api.brand.protocol.ReqBrandList.RespBrandList;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@Data
public class ReqBrandInfo implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	
	private String brandId;
    
	@EqualsAndHashCode(callSuper=true)
    @Accessors(chain = true)
    @Data
    public static class RespBrandInfo extends RespBrandList implements Serializable{
    	
    	private static final long serialVersionUID = -4291721599975198242L;
    	
    	
    	
    }
    

}
