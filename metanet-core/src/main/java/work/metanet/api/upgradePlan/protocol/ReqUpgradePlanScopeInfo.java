package work.metanet.api.upgradePlan.protocol;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class ReqUpgradePlanScopeInfo implements Serializable{
	
	private static final long serialVersionUID = -4291721599975198242L;
	
	private String upgradePlanId;
	
	
	@Accessors(chain = true)
	@Data
	public static class RespUpgradePlanScopeInfo implements Serializable{
		
		private static final long serialVersionUID = -4291721599975198242L;
		private String upgradePlanId;
		private String brandId;
		private String brandName;
		private String modelScope;
		private String models;
		private String modelNames;
		
	}
	
}