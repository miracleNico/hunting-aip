package work.metanet.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ConstFeedbackProcessStatus {
	
	NO("未处理"),
	YES("已处理")
	;
	
	private String text;
	
}
