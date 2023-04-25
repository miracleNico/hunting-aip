package work.metanet.constant;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ConstSource {

	IMPORT("0","后台导入"),
	ENTRY("1","接口记录");
	
	private String val;
	private String text;
	
	public static String getText(String val){
		if(StringUtils.isNotEmpty(val)){
			ConstSource[] constSources  = ConstSource.values();
			for (ConstSource c : constSources) {
				if(val.equals(c.getVal()))
					return c.getText();
			}
		}
		return "";
	}
	
}
