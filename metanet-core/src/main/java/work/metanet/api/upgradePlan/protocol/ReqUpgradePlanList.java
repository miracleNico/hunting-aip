package work.metanet.api.upgradePlan.protocol;

import java.io.Serializable;

import work.metanet.api.upgradePlan.protocol.ReqUpgradePlanInfo.RespUpgradePlanInfo;
import work.metanet.base.Paging;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
@Data
public class ReqUpgradePlanList extends Paging implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	
	//模糊定位(like id|name)
	private String upgradePlan;
	private String appId;
	private String toVersionId;
	private Boolean enforce;
	private Boolean sendStatus;
	private String deviceScope;
	private String startUpgradeTime;
	private String endUpgradeTime;
	private String startTime;
	private String endTime;
	private String remark;
	
	@EqualsAndHashCode(callSuper=true)
    @Accessors(chain = true)
    @Data
    public static class RespUpgradePlanList extends RespUpgradePlanInfo implements Serializable{
    	
    	private static final long serialVersionUID = -4291721599975198242L;
    	
        
        
    	
    }
    

}
