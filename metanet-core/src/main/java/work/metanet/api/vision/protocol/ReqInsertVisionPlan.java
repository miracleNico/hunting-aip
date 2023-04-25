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
 * @date 2021年05月18日16:55
 */
@Data
@Accessors
@ApiModel("请求-新增视力计划")
public class ReqInsertVisionPlan extends VisionPlanBase implements Serializable {

    @NotBlank(message = "用户序列号不能为空")
    @ApiModelProperty(value = "用户序列号", required = true , example = "2734086da3a945d5baef67b1f5f0041f")
    private String userId;
}

