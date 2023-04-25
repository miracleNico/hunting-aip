package work.metanet.api.upgradePlan.protocol;

import java.io.Serializable;
import java.util.List;

import work.metanet.api.upgradePlan.protocol.ReqUpgradePlanScopeInfo.RespUpgradePlanScopeInfo;

import lombok.Data;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@Data
public class ReqUpgradePlanInfo implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	
	private String upgradePlanId;
    
    @Accessors(chain = true)
    @Data
    public static class RespUpgradePlanInfo implements Serializable{
    	
    	private static final long serialVersionUID = -4291721599975198242L;
    	
    	private String upgradePlanId;
    	private String upgradePlanName;
    	private String upgradeTime;
    	private String appId;
    	private String appName;
    	private String startVersionId;
    	private String startVersionCode;
    	private String endVersionId;
    	private String endVersionCode;
    	private String toVersionId;
    	private String toVersionCode;
    	private String deviceScope;
    	private Boolean enforce;
    	private Boolean sendStatus;
        private String remark;
        private String createTime;
        private String updateTime;
    	private List<RespUpgradePlanScopeInfo> upgradePlanScopeInfoList;
    	
    }
    

}
