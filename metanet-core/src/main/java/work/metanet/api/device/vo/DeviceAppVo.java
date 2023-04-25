package work.metanet.api.device.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class DeviceAppVo implements Serializable{
	
	private static final long serialVersionUID = 9121538470530223191L;
	
	private String deviceAppId;
	private String deviceId;
	private String appId;
    private String snCode;
    private String activateTime;
	private String createTime;
	private String appName;
	
}