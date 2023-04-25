package work.metanet.api.upgradePlan.protocol;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ReqSendUpgradePlan implements Serializable{
	
	private static final long serialVersionUID = 8772405211506971416L;
	
	@NotBlank(message = "升级计划id不能为空")
	private String upgradePlanId;
	@NotNull(message = "发布状态不能为空")
	private Boolean sendStatus;
	
	
}
