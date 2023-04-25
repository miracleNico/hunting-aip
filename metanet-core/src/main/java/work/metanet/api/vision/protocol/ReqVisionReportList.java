package work.metanet.api.vision.protocol;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import work.metanet.api.vision.protocol.ReqVisionReportInfo.RespVisionReportInfo;
import work.metanet.base.Paging;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author EdisonFeng
 * @Description 类ReqVisionReportList
 * @DateTime 2021年4月21日
 * Copyright(c) 2021. All Rights Reserved
 */
@ApiModel("请求-视力报告列表")
@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
@Data
public class ReqVisionReportList extends Paging implements Serializable {
	/**
	 * UID序列号
	 */
	private static final long serialVersionUID = -1161646536259146192L;

	/**
     * 眼睛序列号
     */	
	@ApiModelProperty(value = "眼睛id,UUID", required = true, example = "48a66898a83c11eb943f00ff71c9db07")
	@NotBlank(message = "眼睛id不能为空")
	private String eyeId;
	
    /**
     * 判定结果（null未判定 0正常 1警戒 2危险 3高危 4其他异常）
     */	
	@ApiModelProperty(value = "判定结果（null未判定 0正常 1警戒 2危险 3高危 4其他异常）", example = "0")
	private int judgementResult;
	
    /**
     * 小数记录
     */	
	@ApiModelProperty(value = "小数记录", example = "0.8")
	private double decimalRecord;
	
    /**
     * 对数记录，5分制
     */	
	@ApiModelProperty(value = "对数记录，5分制", example = "4.9")
	private double logarithmRecord;
	
//    /**
//     * 球镜屈光度S(单位D)
//     */	
//	@ApiModelProperty(value = "球镜屈光度S(单位D)")
//	private double diopterForSph = 0;
//	
//    /**
//     * 柱镜屈光度C(单位D)
//     */	
//	@ApiModelProperty(value = "柱镜屈光度C(单位D)")
//	private double diopterForCyl = 0;
//	
//    /**
//     * 角膜屈光度(单位D)
//     */	
//	@ApiModelProperty(value = "角膜屈光度(单位D)")
//	private double cornealDiopter = 0;
//	
//    /**
//     * 晶体度(单位D)
//     */	
//	@ApiModelProperty(value = "晶体度(单位D)")
//	private double crystalDiopter = 0;
//	
//    /**
//     * 散光轴度A
//     */	
//	@ApiModelProperty(value = "散光轴度A")
//	private double axisDegreesForAstigmatism = 0;
	
    /**
     * 建议或诊断
     */
//	@ApiModelProperty(value = "建议或诊断")
//	private double proposal;
	
    /**
     * 报告序列号
     */
//	@ApiModelProperty(value = "视力报告id", required = true, example = "d62c4636ada84022be827c63d3c89fed")
//	@NotBlank(message = "视力报告id不能为空")
//	private String reportId;
	
    /**
     * 创建时间
     */	
	@ApiModelProperty(value = "创建时间", example = "2021-05-01 12:30:59")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime = new Date();
	
    @ApiModel("响应-视力报告列表")
    @EqualsAndHashCode(callSuper=true)
    @Accessors(chain = true)
    @Data
    public static class RespVisionReportList extends RespVisionReportInfo implements Serializable{
		/**
		 * UID
		 */
		private static final long serialVersionUID = 4767637198599862591L;
		
//		private String appInfos;
    }
}
