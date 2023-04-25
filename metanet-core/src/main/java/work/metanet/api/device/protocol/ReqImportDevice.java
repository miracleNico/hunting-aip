package work.metanet.api.device.protocol;

import java.io.Serializable;

import lombok.Data;

@Data
public class ReqImportDevice implements Serializable{
	
	private static final long serialVersionUID = 478287614852354005L;
	
	private String deviceName;
	private String serialNumber;
	private String wirelessMac;
	private String modelName;
	private String brandName;
	private String packageName;
	private String uuid;
	private String wiredMac;
	private String imei;
	private String imei2;
	private String bluetooth;
	private String firmwareInfo;
	
}
