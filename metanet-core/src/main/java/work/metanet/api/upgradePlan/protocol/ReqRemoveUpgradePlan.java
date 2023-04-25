package work.metanet.api.upgradePlan.protocol;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ReqRemoveUpgradePlan implements Serializable{
	
	private static final long serialVersionUID = 72288590559616141L;
	
	@NotBlank(message = "计划不能为空")
	private String upgradePlanId;

}
