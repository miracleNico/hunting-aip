package work.metanet.api.model.protocol;

import java.io.Serializable;

import work.metanet.api.model.protocol.ReqModelList.RespModelList;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@Data
public class ReqModelInfo implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	
	private String modelId;
    
	@EqualsAndHashCode(callSuper=true)
    @Accessors(chain = true)
    @Data
    public static class RespModelInfo extends RespModelList implements Serializable{
    	
    	private static final long serialVersionUID = -4291721599975198242L;
    	
    	
    	
    }
    

}
