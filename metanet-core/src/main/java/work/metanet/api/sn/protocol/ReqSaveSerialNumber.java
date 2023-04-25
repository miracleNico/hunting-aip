package work.metanet.api.sn.protocol;

import java.io.Serializable;

import org.hibernate.validator.constraints.Range;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@Data
public class ReqSaveSerialNumber implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	
	private String snCode;
    private String remark;
    private Integer snCodeNum;
    private String appId;
    @Range(min = 1, max = 99999,message = "激活码使用次数不在1-99999这个范围哦")
    private Integer maxUseNumber;
	
    
	@EqualsAndHashCode(callSuper=true)
    @Accessors(chain = true)
    @Data
    public static class RespSaveSerialNumber extends ReqSaveSerialNumber implements Serializable{
    	
    	private static final long serialVersionUID = -4291721599975198242L;
    	
    	
    	
    }
    

}
