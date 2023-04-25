/**
 * @Description 文件：ReqAddVisionReportManually.java
 * @author EdisonFeng
 * @DateTime 2021年5月14日
 * Copyright(c) 2021. All Rights Reserved
 */
package work.metanet.api.vision.protocol;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import work.metanet.model.VisionReportBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
@ApiModel("请求-家长端手工新增测试结果")
@Accessors(chain = true)
@Data
public class ReqAddVisionReportManually extends VisionReportBase {
	/**
     * 第三方应用系统ID
     */	
	@ApiModelProperty(value = "第三方应用系统ID", required = true, example = "f1e247a486ef4c6ebc34cba4f775e924")
	private String appID;
	
    /**
     * 眼睛拥有者ID，用户ID，来自第三方系统，后台自动同步至username
     */	
	@ApiModelProperty(value = "第三方系统用户ID，同步至用户名username", required = false, example = "1234567890")
	private String ownerId;
	
    /**
     * 眼睛拥有者姓名，用户姓名，来自第三方系统
     */	
	@ApiModelProperty(value = "第三方系统用户姓名，同步至昵称nickname", example = "Edison Feng")
	private String ownerName;
	
    /**
     * 眼睛拥有者电话，用户电话，来自第三方系统
     */
	@ApiModelProperty(value = "第三方系统用户电话", required = true, example = "14764725185")
	private String ownerTel;
	
    /**
     * 眼睛拥有者年龄，用户年龄，来自第三方系统
     */	
	@ApiModelProperty(value = "第三方系统用户年龄", example = "16")
	private int ownerAge;
	
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
	@ApiModelProperty(required = true, value = "设备类型（0视力表灯箱、1手机、2平板、3书桌、4电视、5台灯、6其他）", example = "0")
	@NotBlank(message = "设备类型不能为空")
	private int deviceType;
	
    /**
     * 测试距离（单位米）
     */	
	@ApiModelProperty(required = true, value = "测试距离（单位米）", example = "5.0")
	@NotBlank(message = "测试距离不能为空")
	private double distance;
	
    /**
     * 环境光照强度
     */	
	@ApiModelProperty(value = "环境光照强度（流明）", example = "500")
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
	@ApiModelProperty(required = true, value = "测试模式（0自测模式，1家长协助模式，2医生协助模式）", example = "2")
	@NotBlank(message = "测试模式不能为空")
	private int testMode;

	@ApiModel("响应-保存测试报告信息")
    @Accessors(chain = true)
    @Data
    public static class RespAddVisionReportManually implements Serializable {

		private static final long serialVersionUID = 8463822353923900682L;
		@ApiModelProperty(value = "第三方应用系统ID", example = "f1e247a486ef4c6ebc34cba4f775e924")
		private String appID;

		@ApiModelProperty(value = "用户ID", example = "f1e247a486ef4c6ebc34cba4f775e924")
		private String userId;
	    
		@ApiModelProperty(value = "左眼信息ID", example = "f1e247a486ef4c6ebc34cba4f775e924")
		private String leftEyeID;

		@ApiModelProperty(value = "左眼测试活动序列号", example = "e7e6dfb0a83911eb943f00ff71c9db07")
		private String testIdForLeftEye;

		@ApiModelProperty(value = "左眼报告序列号", example = "48a66898a83c11eb943f00ff71c9db07")
		private String reportIdForLeftEye;
		
		@ApiModelProperty(value = "右眼信息ID", example = "f1e247a486ef4c6ebc34cba4f775e924")
		private String rightEyeID;

		@ApiModelProperty(value = "右眼测试活动序列号", example = "e7e6dfb0a83911eb943f00ff71c9db07")
		private String testIdForRightEye;

		@ApiModelProperty(value = "右眼报告序列号", example = "48a66898a83c11eb943f00ff71c9db07")
		private String reportIdForRightEye;
    }
}