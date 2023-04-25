/**
 * @Description 文件：ReqSaveEyestrain.java
 * @author EdisonFeng
 * @DateTime 2021年5月14日
 * Copyright(c) 2021. All Rights Reserved
 */
package work.metanet.api.vision.protocol;

import java.io.Serializable;

import work.metanet.model.EyeBase;
import work.metanet.model.EyestrainBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @Description 请求类：用眼活动保存
 * @author EdisonFeng
 * @DateTime 2021年5月14日
 * Copyright(c) 2021. All Rights Reserved
 */
@Data
@Accessors
public class ReqSaveEyestrain extends EyestrainBase implements Serializable {

	private static final long serialVersionUID = 6428990664089271346L;
    
    @ApiModel("响应-用眼活动保存")
	@Data
    public static class RespSaveEyestrain implements Serializable{

		private static final long serialVersionUID = 1022682018915177096L;

		/**
	     * 用户序列号，关联用户信息
	     */	
		@ApiModelProperty(value = "用户序列号，关联用户信息", example = "e7e6dfb0a83911eb943f00ff71c9db07")
		private String userId;

		/**
	     * 用眼活动序列号
	     */	
		@ApiModelProperty(value = "眼睛活动序列号", example = "48a66898a83c11eb943f00ff71c9db07")
		private String eyestrainId;

		/**
		 * 眼睛序列号
		 */
		@ApiModelProperty(value = "眼睛序列号",example = "00401030de1340439729a99e0f2f1111")
		private String eyeId;
	}
}
