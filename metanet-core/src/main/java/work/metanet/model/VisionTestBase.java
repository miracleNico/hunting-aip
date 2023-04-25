package work.metanet.model;

import java.util.Date;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author EdisonFeng
 * @Description 类VisonTestBase, Model：t_vision_test 的基类
 * @DateTime 2021年4月21日
 * Copyright(c) 2021. All Rights Reserved
 */
@Accessors(chain = true)
@Data
public class VisionTestBase extends Base {
	/**
     * 眼睛序列号，关联用户眼睛信息
     */	
	@ApiModelProperty(required = true, value = "眼睛序列号，关联用户眼睛信息", example = "fef63f64a83a11eb943f00ff71c9db07")
	@NotBlank(message = "眼睛序列号不能为空")
	private String eyeId;
	
    /**
     * 测试日期和时间
     */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(required = true, value = "测试日期和时间", example = "2021-05-01 12:30:59")
	@NotBlank(message = "测试日期和时间不能为空")
	private Date dateTime;
	
    /**
     * 设备类型（0视力表灯箱、1手机、2平板、3书桌、4电视、5台灯、6其他）
     */	
	@ApiModelProperty(required = true, value = "设备类型（0视力表灯箱、1手机、2平板、3书桌、4电视、5台灯、6其他）", example = "1")
	@NotBlank(message = "设备类型不能为空")
	private int deviceType;
	
    /**
     * 测试距离（单位米）
     */	
	@ApiModelProperty(required = true, value = "测试距离（单位米）", example = "2.0")
	@NotBlank(message = "测试距离不能为空")
	private double distance;
	
    /**
     * 环境光照强度
     */	
	@ApiModelProperty(value = "环境光照强度", example = "0")
	private double envLightIntensity;
	
    /**
     * 测试持续时长（单位秒）
     */	
	@ApiModelProperty(required = true, value = "测试持续时长（单位秒）", example = "120")
	@NotBlank(message = "测试持续时长不能为空")
	private int timeDuration;
	
    /**
     * 测试模式（0自测模式，1家长协助模式，2医生协助模式）
     */	
	@ApiModelProperty(required = true, value = "测试模式（0自测模式，1家长协助模式，2医生协助模式）", example = "0")
	@NotBlank(message = "测试模式不能为空")
	private int testMode;
	
    /**
     * 测试序列号（修改时必需）
     */	
	@ApiModelProperty(value = "测试序列号（修改时必需）", example = "a5835d19a83c11eb943f00ff71c9db07")
	@Id
	private String testId;
}
