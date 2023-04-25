
package work.metanet.api.vision.protocol;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description 请求类：删除用眼活动
 * @author EdisonFeng
 * @DateTime 2021年5月14日
 * Copyright(c) 2021. All Rights Reserved
 */
@Accessors(chain = true)
@Data
public class ReqRemoveEyestrain implements Serializable {

	private static final long serialVersionUID = -631340350015607891L;
	
	@ApiModelProperty(value = "用眼活动id,UUID", required = true, example = "d62c4636ada84022be827c63d3c89fed")
	@NotBlank(message = "eyestrainId不能为空")
    private String eyestrainId;
}
