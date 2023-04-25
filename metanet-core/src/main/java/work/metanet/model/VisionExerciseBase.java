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
 * @Description 类VisionReportBase, Model:t_vision_report的基类
 * @DateTime 2021年5月14日
 * Copyright(c) 2021. All Rights Reserved
 */
@Accessors(chain = true)
@Data
public class VisionExerciseBase extends Base {
    /**
     * 用户序列号，关联用户信息
     */
    @ApiModelProperty(value = "用户序列号，关联用户信息", example = "e7e6dfb0a83911eb943f00ff71c9db07")
    private String userId;

    /**
     * 锻炼日期和时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty( value = "测试日期和时间", example = "2021-05-01 12:30:59")
//	@NotBlank(message = "测试日期和时间不能为空")
    private Date dateTime;

    /**
     * 锻炼类型（-1所有类型、0眼保健操、1眼肌锻炼、2眼球放松、3新型眼操、4其他），默认为0
     */
    @ApiModelProperty(value = "锻炼类型（-1所有类型、0眼保健操、1眼肌锻炼、2眼球放松、3新型眼操、4其他），默认为0", example = "0")
//	@NotBlank(message = "锻炼类型不能为空")
    private int exerciseType;

    /**
     * 持续时长（单位秒）
     */
    @ApiModelProperty(value = "持续时长（单位秒）", example = "120")
//	@NotBlank(message = "持续时长不能为空")
    private int timeDuration;

    /**
     * 锻炼序列号（修改时必需）
     */
    @ApiModelProperty(value = "锻炼序列号（修改时必需）", example = "a5835d19a83c11eb943f00ff71c9db07")
    @Id
    private String exerciseId;

    /**
     * 锻炼内容ID
     */
    @ApiModelProperty(value = "锻炼内容ID")
    private String contentId;

}
