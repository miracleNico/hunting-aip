package work.metanet.api.vision.protocol;

import work.metanet.model.VisionPlanBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author nicely
 * @Description
 * @date 2021年05月18日14:12
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "请求-计划详情")
public class ReqVisionPlanInfo implements Serializable {

    @NotBlank(message = "计划序列号不能为空")
    @ApiModelProperty(value = "计划Id",required = true , example = "878c068db6da11eb8b765254006d7470")
    private String planId;

    @ApiModelProperty(value = "用户序列号" ,required = false , example = "2734086da3a945d5baef67b1f5f0041f")
    private String userId;

    @Data
    @Accessors(chain = true)
    @ApiModel(value = "响应-计划详情")
    public static class RespVisionPlanInfo extends VisionPlanBase implements Serializable {
        private static final long serialVersionUID = 1L;
    }
}

