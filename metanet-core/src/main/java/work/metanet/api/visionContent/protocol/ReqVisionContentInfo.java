package work.metanet.api.visionContent.protocol;

import work.metanet.model.VisionContentBase;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Accessors(chain = true)
@Data
public class ReqVisionContentInfo extends VisionContentBase implements Serializable {

    @ApiModelProperty(value = "内容ID")
    private String contentId;

    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode(callSuper = true)
    @Accessors(chain = true)
    @Data
    public static class RespVisionContentInfo extends VisionContentBase implements Serializable {
        private static final long serialVersionUID = 1L;
    }

}
