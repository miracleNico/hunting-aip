/**
 * 
 */
package work.metanet.api.vision.protocol;

import java.io.Serializable;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import work.metanet.api.vision.protocol.ReqEyeInfo.RespEyeInfo;
import work.metanet.model.EyeBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Edison F.
 * @Description 请求-保存眼睛信息
 * @DateTime 2021/04/20
 */
public class ReqSaveEye extends EyeBase implements Serializable {
	private static final long serialVersionUID = 7901142010944920413L;

	@ApiModel("响应-保存眼睛信息")
    @Accessors(chain = true)
    @Data
    public static class RespSaveEye implements Serializable{
		private static final long serialVersionUID = 912494819750483728L;
		
		/**
	     * 用户序列号，关联用户信息
	     */	
		@ApiModelProperty(value = "用户序列号，关联用户信息", example = "e7e6dfb0a83911eb943f00ff71c9db07")
		private String userId;
		
	    /**
	     * 眼睛拥有者ID，用户ID，来自第三方系统
	     */	
		@ApiModelProperty(value = "用户ID，来自第三方系统", example = "1234567890")
		private String ownerId;

		/**
	     * 类型（0左眼,1右眼）
	     */	
		@ApiModelProperty(value = "类型（0左眼,1右眼）", example = "0")
		private int type;
		
		/**
	     * 眼睛序列号
	     */	
		@ApiModelProperty(value = "眼睛序列号", example = "48a66898a83c11eb943f00ff71c9db07")
		private String eyeId;
    }
}
