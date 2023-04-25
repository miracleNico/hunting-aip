package work.metanet.api.channel.protocol;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@Data
public class ReqChannelInfo implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	private String channelId;
    
    @Accessors(chain = true)
    @Data
    public static class RespChannelInfo implements Serializable{
    	private static final long serialVersionUID = -4291721599975198242L;
    	private String channelId;
        private String channelName;
        private String remark;
        private String createTime;
    	private String username;
    	private List<String> roleIdList;
    }
    

}
