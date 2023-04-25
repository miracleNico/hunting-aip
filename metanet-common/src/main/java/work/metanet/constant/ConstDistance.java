package work.metanet.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description 设备类型，枚举类
 * @author EdisonFeng
 * @DateTime 2021年4月25日
 * Copyright(c) 2021. All Rights Reserved
 */
@AllArgsConstructor
@Getter
public enum ConstDistance {
	CLOSE("近距离", 0.5), //0.5米
	MEDIUM("中距离", 2.0), //2米
	DISTINT("远距离", 5.0) //5米
	;
	
	private String key;
	private double value;
}
