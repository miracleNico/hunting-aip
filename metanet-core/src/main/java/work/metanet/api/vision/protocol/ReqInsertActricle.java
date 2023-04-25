package work.metanet.api.vision.protocol;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author nicely
 * @Description
 * @date 2021年05月20日10:56
 */
@Data
@Accessors
public class ReqInsertActricle implements Serializable {

    @ApiModelProperty(value = "接收文本")
    String data;

    @ApiModelProperty(value = "修改时原文件地址")
    String eyesArticleUrl;

    @ApiModelProperty(value = "文章标题")
    String title;
}

