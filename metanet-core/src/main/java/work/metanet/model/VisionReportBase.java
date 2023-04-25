package work.metanet.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author EdisonFeng
 * @Description 类VisionReportBase, Model:t_vision_report的基类
 * @DateTime 2021年4月21日
 * Copyright(c) 2021. All Rights Reserved
 */
@Accessors(chain = true)
@Data
public class VisionReportBase extends Base {

	/**
     * 测试活动序列号，关联测试活动
     */	
	@ApiModelProperty(required = true, value = "测试活动序列号", example = "a5835d19a83c11eb943f00ff71c9db07")
	@NotBlank(message = "测试活动序列号不能为空")
	private String testId;
	
    /**
     * 判定结果（默认null未判定 0正常 1警戒 2危险 3高危 4其他异常）
     */	
	@ApiModelProperty(value = "默认null：未判定 0：正常 1：警戒 2：危险 3：高危 4：其他异常", example = "0")
	private int judgementResult;
	
    /**
     * 小数记录
     */	
	@ApiModelProperty(required = true, value = "小数记录", example = "0.8")
	@NotBlank(message = "小数记录不能为空")
	private double decimalRecord;
	
    /**
     * 对数记录，5分制
     */	
	@ApiModelProperty(required = true, value = "对数记录，5分制", example = "4.9")
	@NotBlank(message = "对数记录不能为空")
	private double logarithmRecord;
	
//    /**
//     * 球镜屈光度S(单位D)
//     */	
//	@ApiModelProperty(value = "球镜屈光度S(单位D)", example = "0")
//	private double diopterForSph;
//	
//    /**
//     * 柱镜屈光度C(单位D)
//     */	
//	@ApiModelProperty(value = "柱镜屈光度C(单位D)", example = "0")
//	private double diopterForCyl;
//	
//    /**
//     * 角膜屈光度(单位D)
//     */	
//	@ApiModelProperty(value = "角膜屈光度(单位D)", example = "0")
//	private double cornealDiopter;
//	
//    /**
//     * 晶体度(单位D)
//     */	
//	@ApiModelProperty(value = "晶体度(单位D)", example = "0")
//	private double crystalDiopter;
//	
//    /**
//     * 散光轴度A
//     */	
//	@ApiModelProperty(value = "散光轴度A", example = "0")
//	private double axisDegreesForAstigmatism;
	
    /**
     * 建议或诊断
     */
	@ApiModelProperty(value = "建议或诊断", example = "警戒阶段，需要注意保持合理用眼时间和用眼卫生")
	private String proposal;
	
    /**
     * 报告序列号（修改时必需）
     */
	@ApiModelProperty(value = "报告序列号（修改时必需）", example = "f8fd9c05a83c11eb943f00ff71c9db07")
	private String reportId;
	
	/**
     * 眼睛类型（0左眼,1右眼）
     */	
	@ApiModelProperty(value = "眼睛类型（0左眼,1右眼）", example = "0")
	private int type;
}
