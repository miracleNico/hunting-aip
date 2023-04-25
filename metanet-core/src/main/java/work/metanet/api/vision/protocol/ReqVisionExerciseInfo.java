/**
 * @Description 文件：ReqVisionExerciseInfo.java
 * @author EdisonFeng
 * @DateTime 2021年5月17日
 * Copyright(c) 2021. All Rights Reserved
 */
package work.metanet.api.vision.protocol;

import work.metanet.model.VisionExerciseBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author EdisonFeng
 *
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "请求-锻炼活动信息")
public class ReqVisionExerciseInfo implements Serializable{

    @ApiModelProperty(value = "锻炼活动id,UUID(必须)",required = true ,example = "9ebe30dbb77611eb8b765254006d7470")
    @NotBlank(message = "锻炼活动Id不能为空")
    private String exerciseId;

    @ApiModelProperty(value = "用户id,UUID", example = "2734086da3a945d5baef67b1f5f0041f")
    private String userId;

    @Data
    @Accessors
    @ApiModel(value = "响应-锻炼活动信息")
    public static class RespVisionExerciseInfo extends VisionExerciseBase implements Serializable{
		private static final long serialVersionUID = 1L;

		@ApiModelProperty(value = "每日锻炼次数")
		private int exerciseCount;

    }

}
