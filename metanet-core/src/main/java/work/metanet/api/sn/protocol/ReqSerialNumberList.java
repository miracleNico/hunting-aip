package work.metanet.api.sn.protocol;

import java.io.Serializable;

import work.metanet.base.Paging;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
@Data
public class ReqSerialNumberList extends Paging implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	
	private String snCode;
	private String channelId;
	private String appId;
	private String startTime;
	private String endTime;
	private String remark;
	private Boolean useStatus;
    
    @Accessors(chain = true)
    @Data
    public static class RespSerialNumberList implements Serializable{
		
		private static final long serialVersionUID = 6799409157978436034L;
		
		private String snCode;
    	private Boolean useStatus;
    	private Integer maxUseNumber;
    	private Integer useNumber;
    	private String appId;
    	private String appName;
        private String remark;
        private String createTime;
        private String updateTime;
    	
    }
    

}
