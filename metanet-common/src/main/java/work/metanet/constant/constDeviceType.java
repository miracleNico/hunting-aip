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
public enum constDeviceType {
	LIGHT_BOX("视力表灯箱"),
	MOBILE("手机"),
	TABLET("平板电脑"),
	SMART_DESK("智能书桌"),
	TV("电视"),
	TABLE_LAMP("台灯")
	;
	
	private String text;
}
