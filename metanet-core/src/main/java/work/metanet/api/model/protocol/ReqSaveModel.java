package work.metanet.api.model.protocol;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@Data
public class ReqSaveModel implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	
	private String modelId;
	private String brandId;
	private String modelName;
	private String source;
    private String remark;
	
    
	@EqualsAndHashCode(callSuper=true)
    @Accessors(chain = true)
    @Data
    public static class RespSaveModel extends ReqSaveModel implements Serializable{
    	
    	private static final long serialVersionUID = -4291721599975198242L;
    	
    	
    	
    }
    

}
