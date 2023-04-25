package work.metanet.api.openDevice.protocol;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@ApiModel("请求-开放平台设备认证")
@Accessors(chain = true)
@Data
public class ReqOpenDeviceAuth implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "应用ID")
	@NotBlank(message = "应用ID不能为空")
	private String appId;
	@ApiModelProperty(value = "设备ID")
	@NotBlank(message = "设备ID不能为空")
	private String deviceId;
	@ApiModelProperty(value = "品牌")
	private String brand;
	@ApiModelProperty(value = "型号")
	private String model;
	@ApiModelProperty(value = "mac")
	private String mac;
	@ApiModelProperty(value = "序列号")
	private String sn;
	@ApiModelProperty(value = "业务代码-返回对应CDK")
	private List<String> businessCodes;
	
	@ApiModel("响应-设备认证")
	@Accessors(chain = true)
	@Data
	public static class RespOpenDeviceAuth implements Serializable{	
		private static final long serialVersionUID = 1L;
		private String token;
		private List<String> cdks = new ArrayList<String>();
	}

}
