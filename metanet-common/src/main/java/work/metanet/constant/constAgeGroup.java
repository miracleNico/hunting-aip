
package work.metanet.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description 年龄段（for vision），枚举类
 * @author EdisonFeng
 * @DateTime 2021年4月25日
 * Copyright(c) 2021. All Rights Reserved
 */
@AllArgsConstructor
@Getter
public enum constAgeGroup {
	BIRTH("出生", "0", 0.02), //视力参考0.02
	ONE_MONTH("满月前", "0-1/12", 0.05), //视力参考0.05
	SIX_MONTHS("半岁前", "1/12-0.5", 0.1), //视力参考0.1
	ONE_YEAR("一岁前", "0.5-1", 0.3), //（0.5-1）视力参考0.1~0.3
	THREE_YEARS("三岁前", "1-3", 0.6), //（1-3）视力参考0.3~0.6
	SIX_YEARS("六岁前", "3-6", 0.8), //（3-6）视力参考0.6~0.8
	EIGHT_YEARS("八岁前", "6-8", 1.0), //（6-8）视力参考0.8~1.0
	FOURTEEN_YEARS("14岁前", "8-14", 1.2), //（8-14）视力参考1.0~1.2
	TWENTY_YEARS("20岁前", "14-20", 1.2), //（14-20）视力参考1.2
	ADULT("成年", "20-60", 1.2), //（20-60）视力参考1.2
	OLD_AGE("老年", "60-120", 1.2) //（60-120）视力参考1.2
	;
	
	private String key;
	private String ageRange;
	private double visionValue;
}
