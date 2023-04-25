/**
 * @Description 文件：ReqSaveVisionExercise.java
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
public class ReqSaveVisionExercise extends VisionExerciseBase implements Serializable {


    @Data
    @Accessors(chain = true)
    @ApiModel(value = "响应：锻炼活动保存")
    public static class RespSaveVisionExercise implements Serializable{
		private static final long serialVersionUID = 1L;

        /**
         * 用户序列号，关联用户信息
         */
        @ApiModelProperty(value = "用户序列号，关联用户信息", example = "2734086da3a945d5baef67b1f5f0041f")
        private String userId;

        /**
         * 锻炼活动序列号
         */
        @ApiModelProperty(value = "锻炼活动序列号", example = "9ebe30dbb77611eb8b765254006d7470")
        private String exerciseId;


    }
}
