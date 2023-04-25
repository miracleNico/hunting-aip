package work.metanet.api.device.protocol;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

import work.metanet.api.device.vo.DeviceAppVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@ApiModel("请求-设备详情")
@Accessors(chain = true)
@Data
public class ReqDeviceInfo implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	
	@ApiModelProperty(value = "设备id")
	@NotBlank(message = "设备id不能为空")
	private String deviceId;
	
    
    @ApiModel("响应-设备详情")
    @Accessors(chain = true)
    @Data
    public static class RespDeviceInfo implements Serializable{
    	
    	private static final long serialVersionUID = -4291721599975198242L;
    	
    	@ApiModelProperty(value = "渠道编号")
    	private String channelId;
    	
    	@ApiModelProperty(value = "渠道名称")
    	private String channelName;
    	
    	@ApiModelProperty(value = "品牌编号")
    	private String brandId;
    	
    	@ApiModelProperty(value = "品牌名称")
    	private String brandName;
    	
    	@ApiModelProperty(value = "型号编号")
    	private String modelId;
    	
    	@ApiModelProperty(value = "型号名称")
    	private String modelName;
    	
    	@ApiModelProperty(value = "设备编号")
    	private String deviceId;
    	
    	@ApiModelProperty(value = "设备名称")
    	private String deviceName;
    	
    	@ApiModelProperty(value = "来源-0导入/1记录 ")
    	private String source;
    	
    	@ApiModelProperty(value = "序列号")
    	private String serialNumber;
    	
    	@ApiModelProperty(value = "UUID")
    	private String uuid;
    	
    	@ApiModelProperty(value = "有线mac")
    	private String wiredMac;
    	
    	@ApiModelProperty(value = "无线mac")
    	private String wirelessMac;
    	
    	@ApiModelProperty(value = "启用状态-0禁用/1启用")
    	private Boolean enableStatus;
    	
    	@ApiModelProperty(value = "创建时间")
    	private String createTime;
    	
    	private String updateTime;
    	
    	@ApiModelProperty(value = "IMEI号")
        private String imei;
    	
    	@ApiModelProperty(value = "IMEI2号")
        private String imei2;
    	
    	@ApiModelProperty(value = "蓝牙")
    	private String bluetooth;
    	
    	@ApiModelProperty(value = "固件信息")
    	private String firmwareInfo;
    	
    	@ApiModelProperty(value = "备注")
    	private String remark;
    	
    	private List<DeviceAppVo> deviceApps;
    	
    }
    

}
