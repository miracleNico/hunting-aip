package work.metanet.api.device.protocol;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@ApiModel("请求-设备认证")
@Accessors(chain = true)
@Data
public class ReqDeviceAuth implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	
	@ApiModelProperty(value = "终端序列号")
	private String serialNumber;
    
    @ApiModelProperty(value = "uuid")
    private String uuid;
    
    @ApiModelProperty(value = "mac地址")
    private String mac;
    
    @ApiModelProperty(value = "有线mac")
    private String wiredMac;
    
    @ApiModelProperty(value = "无线mac")
    private String wirelessMac;
    
    @ApiModelProperty(value = "imei")
    private String imei;
    
    @ApiModelProperty(value = "imei2")
    private String imei2;
    
    @ApiModelProperty(value = "蓝牙")
    private String bluetooth;
    
    @ApiModelProperty(value = "固件信息")
    private String firmwareInfo;
    
    @ApiModelProperty(value = "品牌" , required = true)
    @NotBlank(message = "品牌不能为空")
    private String brandName;
    
    @ApiModelProperty(value = "型号" , required = true)
    @NotBlank(message = "型号不能为空")
    private String modelName;
    //从请求头获取
    @JsonIgnore
    private String packageName;
    
    
    @ApiModel("响应-设备认证")
    @Accessors(chain = true)
    @Data
    public static class RespDeviceAuth implements Serializable{
    	private static final long serialVersionUID = -4291721599975198242L;
    	@ApiModelProperty(value = "设备id")
    	private String deviceId;
    	@ApiModelProperty(value = "设备产品id")
    	private String cid;
    }
    
}
