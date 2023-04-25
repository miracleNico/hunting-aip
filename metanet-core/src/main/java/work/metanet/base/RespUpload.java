package work.metanet.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors
public class RespUpload implements Serializable {

    @ApiModelProperty(value = "图片路径")
    private String src;

    @ApiModelProperty(value = "图片名称 （可选）")
    private String title;

}
