package work.metanet.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ConstRoleType {
	
	ADMIN("平台"),CHANNEL("渠道");
	
	private String text;
	
}
