package work.metanet.api.upgradePlan.protocol;

import java.io.Serializable;

import work.metanet.api.upgradePlan.protocol.ReqUpgradePlanInfo.RespUpgradePlanInfo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Accessors(chain = true)
public class ReqSaveUpgradePlan extends RespUpgradePlanInfo implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	
    
	@EqualsAndHashCode(callSuper=true)
    @Accessors(chain = true)
    @Data
    public static class RespSaveUpgradePlan extends RespUpgradePlanInfo implements Serializable{
    	
    	private static final long serialVersionUID = -4291721599975198242L;
    	
    	
    }
    

}
