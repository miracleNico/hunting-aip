package work.metanet.api.vision.protocol;

import work.metanet.model.EyesArticleExerciseBase;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author nicely
 * @Description
 * @date 2021年05月25日11:21
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "请求:关系信息")
public class ReqEyesArticleExerciseRelationInfo extends EyesArticleExerciseBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Data
    @Accessors(chain = true)
    @ApiModel(value = "响应:关系信息")
    public class RespEyesArticleExerciseRelationInfo extends EyesArticleExerciseBase implements Serializable {

        private static final long serialVersionUID = 1L;
    }
}

