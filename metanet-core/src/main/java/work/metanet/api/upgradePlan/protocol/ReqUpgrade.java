package work.metanet.api.upgradePlan.protocol;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("请求-升级")
@Data
public class ReqUpgrade implements Serializable{
	
	private static final long serialVersionUID = 4970717277254729712L;
	
	@ApiModelProperty(value = "设备id-兼容旧APP,保留")
	private String deviceId;
	@ApiModelProperty(value = "品牌名称")
	private String brandName;
	@ApiModelProperty(value = "型号名称")
	private String modelName;
	@ApiModelProperty(value = "无线mac-完善旧数据缺失导致认证失败问题")//luoxi-temp
    private String wirelessMac;
	@ApiModelProperty(value = "序列号-完善旧数据缺失导致认证失败问题")
	private String serialNumber;
	
	@ApiModel("响应-升级")
	@Data
	public static class RespUpgrade implements Serializable{
		
		private static final long serialVersionUID = 4970717277254729712L;
		@ApiModelProperty("升级计划id")
		private String upgradePlanId;
		@ApiModelProperty("下载地址")
		private String url;
		@ApiModelProperty("下载地址类型-0内部/1外部")
		private String urlType;
		@ApiModelProperty("MD5")
		private String md5;
		@ApiModelProperty("文件大小(b)")
		private Long fileSize;
		@ApiModelProperty("版本号")
		private String versionCode;
		@ApiModelProperty("版本名称")
		private String versionName;
		@ApiModelProperty("说明")
		private String instruction;
		@ApiModelProperty("是否强制升级")
		private Boolean enforce;
		

	}

}
