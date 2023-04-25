package work.metanet.api.vision.protocol;

import java.io.Serializable;

import work.metanet.model.VisionReportBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description 类ReqSaveVisionReport
 * @author EdisonFeng
 * @DateTime 2021年4月21日
 * Copyright(c) 2021. All Rights Reserved
 */
public class ReqSaveVisionReport extends VisionReportBase implements Serializable {

	private static final long serialVersionUID = 6701843337034172057L;
	
	@ApiModel("响应-保存测试报告信息")
    @Accessors(chain = true)
    @Data
    public static class RespSaveVisionReport implements Serializable {

		private static final long serialVersionUID = 8463822353923900682L;

		/**
	     * 测试活动序列号
	     */	
		@ApiModelProperty(value = "测试活动序列号", example = "e7e6dfb0a83911eb943f00ff71c9db07")
		private String testId;

		/**
	     * 判定结果（null未判定 0正常 1警戒 2危险 3高危 4其他异常）
	     */	
		@ApiModelProperty(value = "判定结果（null未判定 0正常 1警戒 2危险 3高危 4其他异常）", example = "0")
		private int judgementResult;
		
		/**
	     * 报告序列号
	     */	
		@ApiModelProperty(value = "报告序列号", example = "48a66898a83c11eb943f00ff71c9db07")
		private String reportId;
    }
}
