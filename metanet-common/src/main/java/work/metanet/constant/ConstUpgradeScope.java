package work.metanet.constant;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ConstUpgradeScope {

	ALL("0","所有"),
	SPECIFIED("1","指定");
	
	private String val;
	private String text;
	
	public static String getText(String val){
		if(StringUtils.isNotEmpty(val)){
			ConstUpgradeScope[] constSources  = ConstUpgradeScope.values();
			for (ConstUpgradeScope c : constSources) {
				if(val.equals(c.getVal()))
					return c.getText();
			}
		}
		return "";
	}
	
}
