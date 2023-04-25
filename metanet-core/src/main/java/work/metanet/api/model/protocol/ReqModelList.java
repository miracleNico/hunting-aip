package work.metanet.api.model.protocol;

import java.io.Serializable;

import work.metanet.base.Paging;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
@Data
public class ReqModelList extends Paging implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	
	//模糊定位(like id|name)
	private String model;
	private String brand;
	private String source;
	private String startTime;
	private String endTime;
	private String remark;
    
    @Accessors(chain = true)
    @Data
    public static class RespModelList implements Serializable{
    	
    	private static final long serialVersionUID = -4291721599975198242L;
    	
    	private String modelId;
    	private String modelName;
    	private String brandId;
    	private String brandName;
    	private String source;
        private String remark;
        private String createTime;
    	
    	
    }
    

}
