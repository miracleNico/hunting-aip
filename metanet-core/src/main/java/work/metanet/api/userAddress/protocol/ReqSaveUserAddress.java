package work.metanet.api.userAddress.protocol;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@ApiModel("请求-保存用户收货地址")
@Data
public class ReqSaveUserAddress implements Serializable{
	
	private static final long serialVersionUID = -8106626379651551791L;
	@ApiModelProperty(value = "收件地址id")
	private String userAddressId;
	@ApiModelProperty(value = "收件人", required = true)
	@NotBlank(message = "收件人不能为空")
	private String consignee;
	@ApiModelProperty(value = "电话号码", required = true)
	@NotBlank(message = "电话号码不能为空")
    private String phone;
	@ApiModelProperty(value = "省id", required = true)
	@NotBlank(message = "省不能为空")
    private String provinceId;
	@ApiModelProperty(value = "市id", required = true)
	@NotBlank(message = "市不能为空")
    private String cityId;
	@ApiModelProperty(value = "县id", required = true)
	@NotBlank(message = "县不能为空")
    private String countyId;
	@ApiModelProperty(value = "详细地址", required = true)
	@NotBlank(message = "详细地址不能为空")
    private String address;
	@ApiModelProperty(value = "邮编")
    private String zipCode;
	
	@ApiModel("响应-保存用户收货地址")
	@EqualsAndHashCode(callSuper=true)
	@Accessors(chain = true)
	@Data
	public static class RespSaveUserAddress extends ReqSaveUserAddress implements Serializable {
		private static final long serialVersionUID = 82776184828615678L;
	}
	
	
}
