package work.metanet.api.permission.protocol;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@Data
public class ReqPermissionInfo implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	private String permissionId;
    
    @Accessors(chain = true)
    @Data
    public static class RespPermissionInfo implements Serializable{
    	private static final long serialVersionUID = -4291721599975198242L;
    	private String permissionId;
    	private String permissionName;
    	private String permissionType;
    	private String permissionTag;
    	private String parentId;
        private String apis;
        private Integer sort;
        private String icon;
    	private String remark;
        private String createTime;
        private String updateTime;
    }
    

}
