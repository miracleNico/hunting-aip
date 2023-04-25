package work.metanet.api.admin.protocol;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@Data
public class ReqAdminInfo implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	private String adminId;
    
    @Accessors(chain = true)
    @Data
    public static class RespAdminInfo implements Serializable{
    	private static final long serialVersionUID = -4291721599975198242L;
    	private String adminId;
    	private String username;
    	private Boolean enableStatus;
        private String remark;
        private List<String> roleIdList;
    }
    

}
