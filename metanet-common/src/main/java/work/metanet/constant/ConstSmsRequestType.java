package work.metanet.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@ToString
@Getter
@AllArgsConstructor
public enum ConstSmsRequestType {
	
	LOGIN(1,"630893"),
	REGISTER(2,"630971"),
	CHANGE_PASSWORD(3,"630900"),
	ACCOUNT_CANCEL(4,"630967"),
	WARNING(5,"631155"),
	UNLOCK(6,"726206")
	;
	
	private Integer smsType;
	private String templateID;
	
	public static ConstSmsRequestType setSmsType(int smsType) {
		ConstSmsRequestType[] values  = ConstSmsRequestType.values();
		for (ConstSmsRequestType o : values) {
			if(smsType==o.getSmsType())return o;
		}
		return null;
	}
	
	
}
