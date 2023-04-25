package work.metanet.constant;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ConstVersionType {

	_0("0","全量包"),
	_1("1","拆分包");
	
	private String val;
	private String text;
	
	public static String getText(String val){
		if(StringUtils.isNotEmpty(val)){
			ConstVersionType[] constSources  = ConstVersionType.values();
			for (ConstVersionType c : constSources) {
				if(val.equals(c.getVal()))
					return c.getText();
			}
		}
		return "";
	}
	
}
