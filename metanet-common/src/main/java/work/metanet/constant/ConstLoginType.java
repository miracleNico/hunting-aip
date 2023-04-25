package work.metanet.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@AllArgsConstructor
public enum ConstLoginType {
	
	PHONE_VALIDATE_CODE_LOGIN(1),
	USERNAME_PASSWORD_LOGIN(2),
	;
	
	private long val;
	
	
}
