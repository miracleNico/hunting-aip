package work.metanet.api.vision.protocol;

import work.metanet.model.VisionReportBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Accessors
public class ReqVisionAVG implements Serializable {

    @ApiModelProperty(value = "用户ID",required = true,example = "e7e6dfb0a83911eb943f00ff71c9db07")
    @NotBlank(message = "用户ID不能为空")
    private String userId;

    @Data
    @Accessors
    public static class RespVisionAVG extends VisionReportBase implements Serializable{

        @ApiModelProperty(value = "左右眼视力平均值")
        private double avg;
    }
}
