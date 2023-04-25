/**
 * @Description 文件：ReqRemoveVisionExercise.java
 * @author EdisonFeng
 * @DateTime 2021年5月17日
 * Copyright(c) 2021. All Rights Reserved
 */
package work.metanet.api.vision.protocol;

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
public class ReqRemoveVisionExercise implements Serializable {

    private static final long serialVersionUID = -631340350015607891L;

    @ApiModelProperty(value = "锻炼活动ID,uuid",required = true,example = "9ebe30dbb77611eb8b765254006d7470")
    @NotBlank(message = "id不能为空")
    private String exerciseId;

}
