package work.metanet.api.user.protocol;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "请求-用户信息")
@Data
public class ReqUserInfo implements Serializable{
	
	private static final long serialVersionUID = 595303890147853161L;
	
	private String userId;
	
	
	@ApiModel(value = "响应-用户信息")
	@Data
	public static class RespUserInfo implements Serializable{
		
		private static final long serialVersionUID = 595303890147853161L;
		
		@ApiModelProperty("用户id")
	    private String userId;
		
		@ApiModelProperty("产品id")
		private String appId;

	    @ApiModelProperty("用户名")
	    private String username;

	    @ApiModelProperty("昵称")
	    private String nickName;

	    @ApiModelProperty("生日")
	    private String birthday;

	    @ApiModelProperty("省")
	    private String provinceId;

	    @ApiModelProperty("市")
	    private String cityId;

	    @ApiModelProperty("县")
	    private String countyId;

	    @ApiModelProperty("邮编")
	    private String zipCode;

	    @ApiModelProperty("邮箱")
	    private String email;

	    @ApiModelProperty("性别-0女/1男/2保密")
	    private String gender;

	    @ApiModelProperty("手机号码")
	    private String phone;

	    @ApiModelProperty("年龄")
	    private Integer age;

	    @ApiModelProperty("爱好-多个用/分割")
	    private String hobby;

	    @ApiModelProperty("头像路径")
	    private String headUrl;
	    
	    @ApiModelProperty("启用状态-0禁用/1启用")
	    private Boolean enableStatus;
	    
	    private String remark;
        private String createTime;
		
	}
	
}
