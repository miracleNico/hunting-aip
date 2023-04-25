package work.metanet.api.device.protocol;

import java.io.Serializable;
import java.util.List;

import work.metanet.api.device.vo.DeviceAppVo;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class ReqSaveDevice implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	
    private String deviceId;
    private String deviceName;
    private String serialNumber;
    private String wiredMac;
    private String wirelessMac;
    private String imei;
    private String imei2;
    private String bluetooth;
    private String modelId;
    private String brandId;
    private String uuid;
    private String firmwareInfo;
    private String remark;
    private List<DeviceAppVo> deviceApps;
	

}
