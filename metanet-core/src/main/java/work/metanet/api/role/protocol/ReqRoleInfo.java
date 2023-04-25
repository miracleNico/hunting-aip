package work.metanet.api.role.protocol;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@Data
public class ReqRoleInfo implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	private String roleId;
    
    @Accessors(chain = true)
    @Data
    public static class RespRoleInfo implements Serializable{
    	private static final long serialVersionUID = -4291721599975198242L;
    	private String roleId;
    	private String roleName;
    	private String roleType;
    	private String remark;
        private String createTime;
        private String updateTime;
    }
    

}
