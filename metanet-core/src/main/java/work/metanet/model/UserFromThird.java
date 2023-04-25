
package work.metanet.model;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import work.metanet.api.user.protocol.ReqSyncUserFromThird;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description 来自第三方的用户信息
 * @author EdisonFeng
 * @DateTime 2021年4月26日
 * Copyright(c) 2021. All Rights Reserved
 */
@Accessors(chain = true)
@Data
public class UserFromThird implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 842052624764927919L;

	/**
     * 第三方应用系统ID
     */	
	@ApiModelProperty(value = "第三方应用系统ID", required = true, example = "f1e247a486ef4c6ebc34cba4f775e924")
	private String appID;
	
    /**
     * 眼睛拥有者ID，用户ID，来自第三方系统，后台自动同步至username
     */	
	@ApiModelProperty(value = "第三方系统用户ID，同步至用户名username", required = false, example = "1234567890")
	private String ownerId;
	
    /**
     * 眼睛拥有者姓名，用户姓名，来自第三方系统
     */	
	@ApiModelProperty(value = "第三方系统用户姓名，同步至昵称nickname", example = "Edison Feng")
	private String ownerName;
	
    /**
     * 眼睛拥有者电话，用户电话，来自第三方系统
     */
	@ApiModelProperty(value = "第三方系统用户电话", required = true, example = "14764725185")
	private String ownerTel;
	
    /**
     * 眼睛拥有者年龄，用户年龄，来自第三方系统
     */	
	@ApiModelProperty(value = "第三方系统用户年龄", example = "16")
	private int ownerAge;
	
}
