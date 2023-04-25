
package work.metanet.model;

import java.io.Serializable;
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
 * @Description 视力计划
 * @DateTime 2021年5月14日
 * Copyright(c) 2021. All Rights Reserved
 */
@Data
@Accessors
public class VisionPlanBase extends Base implements Serializable {
	/**
     * 用户序列号，关联用户信息
     */	
	@ApiModelProperty(value = "用户序列号，关联用户信息", example = "e7e6dfb0a83911eb943f00ff71c9db07")
	private String userId;

	/**
	 * 用户计划名称
	 */
	@ApiModelProperty(value = "用户计划名称",example = "第三个五年计划")
	private String planName;

	/**
     * 提醒类型（0年提醒,1月提醒,2周提醒,3天提醒,4小时提醒,5一次性提醒）
     */	
	@ApiModelProperty(value = "提醒类型（0年提醒,1月提醒,2周提醒,3天提醒,4小时提醒,5一次性提醒,6每45分钟提醒,7每30分钟提醒,8每3天提醒）", example = "0")
	private int planType;
	
	/**
     * 计划开始时间，执行计划所规定的活动的开始时间，也是首次提醒的时间点
     */	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "计划开始时间，执行计划所规定的活动的开始时间，也是首次提醒的时间点", example = "2021-05-01 12:30:59")
//	@NotBlank(message = "计划开始时间不能为空")
	private Date startDate;
	
	/**
     * 计划结束时间，执行计划所规定的活动的结束开始时间，最后一次提醒的时间点不能超过计划结束时间
     */	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "计划结束时间，执行计划所规定的活动的结束开始时间，最后一次提醒的时间点不能超过计划结束时间", example = "2021-05-01 12:30:59")
//	@NotBlank(message = "计划结束时间不能为空")
	private Date endDate;
	
	/**
     * 执行模式（0状态栏提醒,1弹出框提醒,2立即强制切换,3倒计时切换可取消）
     */	
	@ApiModelProperty(value = "执行模式（0状态栏提醒,1弹出框提醒,2立即强制切换,3倒计时切换可取消）", example = "0")
	private int actionMode;
	
	/**
     * 计划操作（0视力测试,1眼保健操,2眼肌锻炼,3眼球放松,4新型眼操,5其他）
     */	
	@ApiModelProperty(value = "计划操作（0视力测试,1视力防护,2其他）", example = "0")
	private int planAction;
	
    /**
     * 计划序列号（修改时必需）
     */	
	@ApiModelProperty(value = "计划序列号（修改时必需）", example = "a5835d19a83c11eb943f00ff71c9db07")
	@Id
	private String planId;
}
