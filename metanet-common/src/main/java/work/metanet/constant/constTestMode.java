
package work.metanet.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description 测试模式，枚举类
 * @author EdisonFeng
 * @DateTime 2021年4月25日
 * Copyright(c) 2021. All Rights Reserved
 */
@AllArgsConstructor
@Getter
public enum constTestMode {
	SELF("自测模式"),
	PARENT("家长模式"),
	DOCTOR("医生模式")
	;
	
	private String text;
}
