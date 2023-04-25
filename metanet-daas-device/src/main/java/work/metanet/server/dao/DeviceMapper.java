/**  
 * @Title: OrderMapper.java
 * @Description: TODO
 * @author wanbo
 * @date 2018年3月28日
 */
package work.metanet.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import work.metanet.api.device.protocol.ReqDeviceInfo.RespDeviceInfo;
import work.metanet.api.device.protocol.ReqDeviceList.RespDeviceList;
import work.metanet.api.statistical.vo.ChartVo;
import work.metanet.api.device.protocol.ReqRemoveDevice;
import work.metanet.model.Device;
import work.metanet.server.vo.AuthDeviceParam;
import work.metanet.server.vo.DeviceVo;

import tk.mybatis.mapper.common.Mapper;

public interface DeviceMapper extends Mapper<Device>{
	
	List<ChartVo> appDeviceChart(@Param("channelId")String channelId);
	
	List<ChartVo> appDeviceActiveChart(@Param("channelId")String channelId);
	
	Integer deviceTotal(@Param("channelId")String channelId);
	
	Integer deviceActiveTotal(@Param("channelId")String channelId);
	
	@Update("update t_device set serial_number=#{serialNumber},wireless_mac=#{wirelessMac} where device_id=#{deviceId} and (ISNULL(serial_number) or ISNULL(wireless_mac))")
	int repairDevice(@Param("deviceId")String deviceId,@Param("wirelessMac")String wirelessMac,@Param("serialNumber")String serialNumber);
	
	/**
	 * @Description: luoxi-temp完善旧数据缺失导致认证失败问题
	 * @Author wanbo
	 * @DateTime 2020/05/21
	 */
	@Update("update t_device set serial_number=#{serialNumber} where status=true and (ISNULL(serial_number) or LENGTH(serial_number)=0) and wireless_mac=#{wirelessMac}")
	int repairDeviceSerialNumber(@Param("wirelessMac")String wirelessMac,@Param("serialNumber")String serialNumber);
	
	/**
	 * @Description: 获取认证设备信息
	 * @Author wanbo
	 * @DateTime 2020/05/17
	 */
	DeviceVo getAuthDevice(AuthDeviceParam param);
	
	/**
	 * @Description: 设备详情
	 * @Author wanbo
	 * @DateTime 2019/11/24
	 */
	RespDeviceInfo deviceInfo(Map<String, Object> map);
	
	/**
	 * @Description: 设备列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	List<RespDeviceList> deviceList(Map<String, Object> map);
	
	/**
	 * @Description: 删除设备
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	int removeDevice(@Param("list")List<ReqRemoveDevice> list);
	
	/**
	 * @Description: 启用/禁用设备
	 * @Author wanbo
	 * @DateTime 2019/11/23
	 */
	@Update("update t_device set enable_status=${enableStatus} where device_id=#{deviceId}")
	int enableDevice(@Param("deviceId")String deviceId,@Param("enableStatus")boolean enableStatus);
	
}
