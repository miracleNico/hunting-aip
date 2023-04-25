
package work.metanet.api.vision.protocol;

import java.io.Serializable;

import work.metanet.model.EyeBase;
import work.metanet.model.EyestrainBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description 请求类：用眼活动
 * @author EdisonFeng
 * @DateTime 2021年5月14日
 * Copyright(c) 2021. All Rights Reserved
 */
@ApiModel("请求-用眼活动")
@Accessors(chain = true)
@Data
public class ReqEyestrainInfo implements Serializable {
	private static final long serialVersionUID = -2097428724298247542L;
	
	@ApiModelProperty(value = "用眼活动id,UUID(必须)",required = true ,example = "48a66898a83c11eb943f00ff71c9db07")
	private String eyestrainId;
	
	@ApiModelProperty(value = "用户id,UUID", example = "e7e6dfb0a83911eb943f00ff71c9db07")
	private String userId;	
    
    @ApiModel("响应-眼睛详情")
    public static class RespEyestrainInfo extends EyestrainBase implements Serializable{
    	
		private static final long serialVersionUID = 8691161154073592726L;
    }
}
