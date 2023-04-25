package work.metanet.api.vision.protocol;

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
@ApiModel(value = "请求-删除计划")
public class ReqRemoveVisionPlan implements Serializable {
    private static final long serialVersionUID = 96932835729L;

    @NotBlank(message = "计划序列号不能为空")
    @ApiModelProperty(value = "计划序列号",required = true , example = "878c068db6da11eb8b765254006d7470")
    private String planId;
}

