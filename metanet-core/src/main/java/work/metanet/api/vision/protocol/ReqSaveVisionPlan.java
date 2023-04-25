package work.metanet.api.vision.protocol;

import work.metanet.model.VisionPlanBase;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author nicely
 * @Description
 * @date 2021年05月18日14:13
 */
@Data
@Accessors
@ApiModel(value = "请求-保存计划")
public class ReqSaveVisionPlan extends VisionPlanBase implements Serializable {

    private static final long serialVersionUID = 456781685L;

    @Data
    @Accessors
    @ApiModel(value = "响应-保存计划")
    public static class RespSaveVisionPlan implements Serializable{

        private static final long serialVersionUID = 80993209L;

        /**
         * 计划序列号
         */
        @ApiModelProperty(value = "计划序列号" ,example = "878c068db6da11eb8b765254006d7470")
        private String planId;

        /**
         * 用户序列号
         */
        @ApiModelProperty(value = "用户序列号",example = "2734086da3a945d5baef67b1f5f0041f")
        private String userId;
    }
}

