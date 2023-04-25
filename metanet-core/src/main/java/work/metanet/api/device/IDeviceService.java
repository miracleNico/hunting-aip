package work.metanet.api.device;

import java.util.List;

import work.metanet.api.device.protocol.ReqActivate;
import work.metanet.api.device.protocol.ReqActivate.RespActivate;
import work.metanet.api.device.protocol.ReqDeviceAuth;
import work.metanet.api.device.protocol.ReqDeviceAuth.RespDeviceAuth;
import work.metanet.api.device.protocol.ReqDeviceInfo;
import work.metanet.api.device.protocol.ReqDeviceInfo.RespDeviceInfo;
import work.metanet.api.device.protocol.ReqDeviceList;
import work.metanet.api.device.protocol.ReqDeviceList.RespDeviceList;
import work.metanet.api.device.protocol.ReqEnableDevice;
import work.metanet.api.device.protocol.ReqImportDevice;
import work.metanet.api.device.protocol.ReqRemoveDevice;
import work.metanet.api.device.protocol.ReqSaveDevice;
import work.metanet.api.statistical.vo.ChartVo;
import work.metanet.base.RespPaging;

public interface IDeviceService {
	
	List<ChartVo> appDeviceChart(String channelId) throws Exception;
	
	List<ChartVo> appDeviceActiveChart(String channelId) throws Exception;
	
	Integer deviceTotal(String channelId) throws Exception;
	
	Integer deviceActiveTotal(String channelId) throws Exception;
	
	/**
	 * @Description:luoxi-temp完善旧数据缺失导致认证失败问题 
	 * @Author wanbo
	 * @DateTime 2020/05/21
	 */
	void repairDeviceSerialNumber(String wirelessMac,String serialNumber);

	/**
	 * @Description: 设备认证
	 * @Author wanbo
	 * @DateTime 2019/11/16
	 */
	RespDeviceAuth deviceAuth(ReqDeviceAuth req) throws Exception;
	
	/**
	 * @Description: 设备激活
	 * @Author wanbo
	 * @DateTime 2019/11/14
	 */
	RespActivate activate(ReqActivate req) throws Exception;
	
	/**
	 * @Description: 设备列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespPaging<RespDeviceList> deviceList(ReqDeviceList req) throws Exception;
	
	/**
	 * @Description: 设备详情
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespDeviceInfo deviceInfo(String channelId,ReqDeviceInfo req) throws Exception;
	
	/**
	 * @Description: 设备导入
	 * @Author wanbo
	 * @DateTime 2019/12/03
	 */
	void importDevice(ReqImportDevice req) throws Exception;
	
	/**
	 * @Description: 保存设备
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void saveDevice(ReqSaveDevice req) throws Exception;
	
	/**
	 * @Description: 删除设备
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void removeDevice(List<ReqRemoveDevice> devices) throws Exception;
	
	/**
	 * @Description: 启用/禁用设备
	 * @Author wanbo
	 * @DateTime 2019/11/23
	 */
	void enableDevice(ReqEnableDevice req) throws Exception;
	
}
