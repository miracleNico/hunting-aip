package work.metanet.api.userAddress.protocol;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@ApiModel("请求-用户收货地址信息")
@Data
public class ReqUserAddressInfo implements Serializable{
	
	private static final long serialVersionUID = -8106626379651551791L;
	
	@ApiModel("响应-用户收货地址信息")
	@Accessors(chain = true)
	@Data
	public static class RespUserAddressInfo implements Serializable {
		private static final long serialVersionUID = 82776184828615678L;
		@ApiModelProperty(value = "收件地址id")
		private String userAddressId;
		@ApiModelProperty(value = "收件人")
		private String consignee;
		@ApiModelProperty(value = "电话号码")
	    private String phone;
		@ApiModelProperty(value = "省id")
	    private String provinceId;
		@ApiModelProperty(value = "市id")
	    private String cityId;
		@ApiModelProperty(value = "县id")
	    private String countyId;
		@ApiModelProperty(value = "详细地址")
	    private String address;
		@ApiModelProperty(value = "邮编")
	    private String zipCode;
	}
	
	
}
