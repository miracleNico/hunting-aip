package work.metanet.api.device.protocol;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@ApiModel("请求-设备激活")
@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
@Data
public class ReqActivate extends ReqDeviceAuth implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	@ApiModelProperty(value = "SN码")
	private String snCode;
    
    @ApiModel("响应-设备激活")
    @EqualsAndHashCode(callSuper=true)
    @Accessors(chain = true)
    @Data
    public static class RespActivate extends RespDeviceAuth implements Serializable{
    	private static final long serialVersionUID = -4291721599975198242L;
    	@ApiModelProperty(value = "设备id")
    	private String deviceId;
    	@ApiModelProperty(value = "设备产品id")
    	private String cid;
    }
    

}
