package work.metanet.api.device.protocol;

import java.io.Serializable;

import work.metanet.api.device.protocol.ReqDeviceInfo.RespDeviceInfo;
import work.metanet.base.Paging;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@ApiModel("请求-设备列表")
@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
@Data
public class ReqDeviceList extends Paging implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	
	@ApiModelProperty(value = "渠道id")
	private String channelId;
	
	@ApiModelProperty(value = "品牌")
	private String brand;
	
	@ApiModelProperty(value = "型号")
	private String model;
	
	@ApiModelProperty(value = "产品")
	private String app;
	
	@ApiModelProperty(value = "设备")
	private String device;
	
	@ApiModelProperty(value = "SN码")
	private String snCode;
	
	@ApiModelProperty(value = "序列号")
	private String serialNumber;
	
	@ApiModelProperty(value = "UUID")
	private String uuid;
	
	@ApiModelProperty(value = "MAC地址")
	private String mac;
	
	@ApiModelProperty(value = "来源-0导入/1记录 ")
	private String source;
	
	@ApiModelProperty(value = "时间范围end")
	private String startTime;
	
	@ApiModelProperty(value = "时间范围start")
	private String endTime;
	
	@ApiModelProperty(value = "激活状态-0未激活/1激活")
	private Boolean activeStatus;
	
	@ApiModelProperty(value = "启用状态-0禁用/1启用")
	private Boolean enableStatus;
	
    
    @ApiModel("响应-设备列表")
    @EqualsAndHashCode(callSuper=true)
    @Accessors(chain = true)
    @Data
    public static class RespDeviceList extends RespDeviceInfo implements Serializable{
    	
    	private static final long serialVersionUID = -4291721599975198242L;
    	
    	private String appInfos;
    	
    	
    }
    

}
