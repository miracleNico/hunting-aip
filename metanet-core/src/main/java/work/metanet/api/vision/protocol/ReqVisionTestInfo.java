package work.metanet.api.vision.protocol;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import work.metanet.model.VisionTestBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author EdisonFeng
 * @Description 类ReqVisionTestInfo
 * @DateTime 2021年4月21日
 * Copyright(c) 2021. All Rights Reserved
 */
@ApiModel("请求-视力测试活动详情")
@Accessors(chain = true)
@Data
public class ReqVisionTestInfo implements Serializable {

	private static final long serialVersionUID = -3352207836495758691L;
	
	@ApiModelProperty(value = "视力测试活动id", required = true, example = "a5835d19a83c11eb943f00ff71c9db07")
	@NotBlank(message = "视力测试活动id不能为空")
	private String testId;
    
    @ApiModel("响应-视力测试活动详情")
    public static class RespVisionTestInfo extends VisionTestBase implements Serializable{

		private static final long serialVersionUID = 1215262989802655784L;

    }
}
