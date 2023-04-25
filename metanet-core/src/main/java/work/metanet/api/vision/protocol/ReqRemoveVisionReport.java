package work.metanet.api.vision.protocol;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description 类ReqRemoveVisionReport
 * @author EdisonFeng
 * @DateTime 2021年4月21日
 * Copyright(c) 2021. All Rights Reserved
 */
@Accessors(chain = true)
@Data
public class ReqRemoveVisionReport implements Serializable {
	private static final long serialVersionUID = 2844450905709213384L;
	@ApiModelProperty(value = "报告id,UUID", required = true, example = "d62c4636ada84022be827c63d3c89fed")
	@NotBlank(message = "报告ID不能为空")
    private String reportId;
}
