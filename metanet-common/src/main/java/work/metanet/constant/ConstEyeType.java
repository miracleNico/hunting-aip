package work.metanet.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description 眼睛类型，左右眼，枚举类
 * @author EdisonFeng
 * @DateTime 2021年4月25日
 * Copyright(c) 2021. All Rights Reserved
 */
@AllArgsConstructor
@Getter
public enum ConstEyeType {
	LEFT_EYE("左眼"),
	RIGHT_EYE("右眼")
	;
	
	private String text;
}
