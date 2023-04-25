package work.metanet.api.deviceApp;

public interface IDeviceAppService {
	
	/**
	 * @Description: 设备产品是否激活
	 * @Author wanbo
	 * @DateTime 2020/04/28
	 */
	boolean isActivate(String deviceAppId) throws Exception;
	
	String getDeviceAppId(String deviceId,String packageName) throws Exception;
	
}
