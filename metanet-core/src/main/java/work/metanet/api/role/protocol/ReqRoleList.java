package work.metanet.api.role.protocol;

import java.io.Serializable;

import work.metanet.api.role.protocol.ReqRoleInfo.RespRoleInfo;
import work.metanet.base.Paging;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
@Data
public class ReqRoleList extends Paging implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	private String roleName;
	private String roleType;
	private String remark;
	private String startTime;
	private String endTime;
    
	@EqualsAndHashCode(callSuper=true)
    @Accessors(chain = true)
    @Data
    public static class RespRoleList extends RespRoleInfo implements Serializable{
		private static final long serialVersionUID = 6799409157978436034L;
		private String roleTypeName;
    }
    

}
