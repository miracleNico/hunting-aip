package work.metanet.api.vision.protocol;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import work.metanet.model.VisionTableBase;
import work.metanet.model.VisionTestBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
/**
 * @author EdisonFeng
 * @Description 类ReqVisionTableInfo
 * @DateTime 2021年5月1日
 * Copyright(c) 2021. All Rights Reserved
 */
@ApiModel("请求-视力表查询")
@Accessors(chain = true)
@Data
public class ReqVisionTableInfo implements Serializable {
	private static final long serialVersionUID = 8384681951530319035L;
	@ApiModelProperty(value = "行序号", required = true, example = "0")
	@NotNull(message = "行序号不能为空")
	private int lineSn;
	
    @ApiModel("响应-视力表查询")
    public static class RespVisionTableInfo extends VisionTableBase implements Serializable{
		private static final long serialVersionUID = -5990893466418923214L;

    }
}
