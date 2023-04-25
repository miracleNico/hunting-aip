package work.metanet.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ConstAppType {

	APP("0","APP"),
	FIRMWARE("1","固件");
	
	private String val;
	private String text;
	
}
