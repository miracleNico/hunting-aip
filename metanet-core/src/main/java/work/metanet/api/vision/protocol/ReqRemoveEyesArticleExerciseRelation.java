package work.metanet.api.vision.protocol;

import work.metanet.model.EyesArticleExerciseBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author nicely
 * @Description
 * @date 2021年05月25日11:21
 */
@Data
@Accessors
@ApiModel(value = "请求:删除关系")
public class ReqRemoveEyesArticleExerciseRelation implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "关系id")
    @NotBlank(message = "删除时关系id不能为空")
    private String articleExerciseId;

}

