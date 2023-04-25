package work.metanet.api.openDevice;

import java.util.List;

import work.metanet.api.openDevice.protocol.ReqOpenDeviceAuth;
import work.metanet.api.openDevice.protocol.ReqOpenDeviceEnable;
import work.metanet.api.openDevice.protocol.ReqOpenDeviceAuth.RespOpenDeviceAuth;
import work.metanet.api.openDevice.protocol.ReqOpenDeviceList;
import work.metanet.api.openDevice.protocol.ReqOpenDeviceList.RespOpenDeviceList;
import work.metanet.api.openDevice.vo.OpenDeviceToken;
import work.metanet.api.openDevice.protocol.ReqOpenDeviceRemove;
import work.metanet.base.RespPaging;
import work.metanet.model.OpenDevice;

public interface IOpenDeviceService {
	
	/**
	 * @Description: 设备列表
	 * @Author wanbo
	 * @DateTime 2020/09/27
	 */
	RespPaging<RespOpenDeviceList> deviceList(ReqOpenDeviceList req) throws Exception;
	
	/**
	 * @Description: 删除认证设备
	 * @Author wanbo
	 * @DateTime 2020/09/27
	 */
	void removeDevice(List<ReqOpenDeviceRemove> devices) throws Exception;
	
	/**
	 * @Description: 设备信息
	 * @Author wanbo
	 * @DateTime 2020/09/28
	 */
	OpenDevice info(String openDeviceId) throws Exception;
	
	/**
	 * @Description:启用/禁用设备 
	 * @Author wanbo
	 * @DateTime 2020/09/28
	 */
	void enable(ReqOpenDeviceEnable req) throws Exception;
	
	/**
	 * @Description: 设备认证
	 * @Author wanbo
	 * @DateTime 2020/09/21
	 */
	RespOpenDeviceAuth auth(ReqOpenDeviceAuth req) throws Exception;
	
	String checkToken(String token,OpenDeviceToken openDeviceToken) throws Exception;
	
}
