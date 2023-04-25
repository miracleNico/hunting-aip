package work.metanet.constant;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ConstUrlType {

	_0("0","内部"),
	_1("1","外部");
	
	private String val;
	private String text;
	
	public static String getText(String val){
		if(StringUtils.isNotEmpty(val)){
			ConstUrlType[] constSources  = ConstUrlType.values();
			for (ConstUrlType c : constSources) {
				if(val.equals(c.getVal()))
					return c.getText();
			}
		}
		return "";
	}
	
}
