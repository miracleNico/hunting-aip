
package work.metanet.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description 诊断结果，枚举类
 * @author EdisonFeng
 * @DateTime 2021年4月25日
 * Copyright(c) 2021. All Rights Reserved
 */
@AllArgsConstructor
@Getter
public enum constJudgementResult {
	NORMAL("正常"),
	ALERT("警戒"),
	DANGEROUS("危险"),
	HIGH_RISK("高危"),
	OTHER_ABNORMAL("其他异常")
	;
	
	private String text;
}
