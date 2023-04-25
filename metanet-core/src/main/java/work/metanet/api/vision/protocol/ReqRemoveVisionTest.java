package work.metanet.api.vision.protocol;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
/**
 * @author EdisonFeng
 * @Description 类ReqRemoveVisionTest
 * @DateTime 2021年4月21日
 * Copyright(c) 2021. All Rights Reserved
 */
public class ReqRemoveVisionTest implements Serializable {

	private static final long serialVersionUID = -1890140685924649495L;
	
	@ApiModelProperty(value = "测试id,UUID", required = true, example = "d62c4636ada84022be827c63d3c89fed")
	@NotBlank(message = "测试ID不能为空")
    private String testId;

}
