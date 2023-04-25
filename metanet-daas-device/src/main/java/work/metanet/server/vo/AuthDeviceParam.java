package work.metanet.server.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor(staticName = "of")
@Accessors(chain = true)
@Data
public class AuthDeviceParam implements Serializable{
	
	private static final long serialVersionUID = 2608595058447243146L;
	private String deviceId;
	private String brandName;
	private String modelName;
	private String deviceAuthType;
	private String mac;
	private String serialNumber;
}
