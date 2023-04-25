package work.metanet.api.vision.protocol;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import work.metanet.model.VisionReportBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author EdisonFeng
 * @Description 类ReqVisionReportInfo
 * @DateTime 2021年4月21日
 * Copyright(c) 2021. All Rights Reserved
 */
@ApiModel("请求-视力报告详情")
@Accessors(chain = true)
@Data
public class ReqVisionReportInfo implements Serializable {
	/**
	 * UID序列号
	 */
	private static final long serialVersionUID = 9017916839955764636L;

	@ApiModelProperty(value = "视力报告id", required = true, example = "257a702ca83d11eb943f00ff71c9db07")
	@NotBlank(message = "视力报告id不能为空")
	private String reportId;
	
    @ApiModel("响应-视力报告详情")
    public static class RespVisionReportInfo extends VisionReportBase implements Serializable{

		/**
		 * UID序列号
		 */
		private static final long serialVersionUID = 1397421086001668420L;

    }
}
