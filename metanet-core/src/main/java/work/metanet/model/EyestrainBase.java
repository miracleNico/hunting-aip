
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
 * @Description 用眼活动，或用眼过度中间记录
 * @author EdisonFeng
 * @DateTime 2021年5月14日
 * Copyright(c) 2021. All Rights Reserved
 */
@Accessors(chain = true)
@Data
public class EyestrainBase extends Base implements Serializable {
    /**
     * 用户序列号，关联用户信息
     */
    @ApiModelProperty(value = "用户序列号，关联用户信息", example = "e7e6dfb0a83911eb943f00ff71c9db07")
    private String userId;

    /**
     * 眼睛序列号，关联用户眼睛信息，备用
     */
    @ApiModelProperty(value = "眼睛序列号，关联用户眼睛信息，备用", example = "fef63f64a83a11eb943f00ff71c9db07")
    private String eyeId;

    /**
     * 用眼日期和时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(required = true, value = "用眼日期和时间", example = "2021-05-01 12:30:59")
    @NotBlank(message = "用眼日期和时间不能为空")
    private Date dateTime;

    /**
     * 用眼类型（-1所有类型、0看书、1看黑板、2看手机、3看电脑、4看平板、5看电视、6其他），默认为0
     */
    @ApiModelProperty(required = true, value = "用眼类型（-1所有类型、0看书、1看屏幕、2学习），默认为0", example = "0")
    @NotBlank(message = "用眼类型不能为空")
    private int useType;

    /**
     * 持续时长（单位秒）
     */
    @ApiModelProperty(required = true, value = "持续时长（单位秒）", example = "120")
    @NotBlank(message = "持续时长不能为空")
    private int timeDuration;

    /**
     * 用眼模式（-1所有模式，0自我监督模式，1家长协助模式，2医生协助模式），默认为0
     */
    @ApiModelProperty(required = true, value = "用眼模式（-1所有模式，0自我监督模式，1家长协助模式，2医生协助模式），默认为0", example = "0")
    @NotBlank(message = "用眼模式不能为空")
    private int useMode;

    /**
     * 用眼活动序列号（修改时必需）
     */
    @ApiModelProperty(value = "用眼活动序列号（修改时必需）", example = "a5835d19a83c11eb943f00ff71c9db07")
    @Id
    private String eyestrainId;
}