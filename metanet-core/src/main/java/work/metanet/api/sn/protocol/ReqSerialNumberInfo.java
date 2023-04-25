package work.metanet.api.sn.protocol;

import java.io.Serializable;

import work.metanet.api.sn.protocol.ReqSerialNumberList.RespSerialNumberList;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@Data
public class ReqSerialNumberInfo implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	
	private String snCode;
    
	@EqualsAndHashCode(callSuper=true)
    @Accessors(chain = true)
    @Data
    public static class RespSerialNumberInfo extends RespSerialNumberList implements Serializable{
    	
    	private static final long serialVersionUID = -4291721599975198242L;
    	
    	
    	
    }
    

}
