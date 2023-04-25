package work.metanet.api.openDevice.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Data
public class OpenDeviceToken implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String openDeviceId;
	private String deviceId;
	private String appId;
	private String channelId;
	private Boolean refresh = false;
	
}
