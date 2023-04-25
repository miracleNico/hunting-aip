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
import org.apache.ibatis.annotations.Select;

import work.metanet.api.device.vo.DeviceAppVo;
import work.metanet.model.DeviceApp;

import tk.mybatis.mapper.common.Mapper;

public interface DeviceAppMapper extends Mapper<DeviceApp>{
	
	DeviceApp getDeviceApp(Map<Object, Object> map);
	
	@Select("select * from t_device_app where device_id=#{deviceId}")
	List<DeviceApp> deviceAppList(String deviceId);
	
	List<DeviceAppVo> deviceAppDeailList(@Param("channelId")String channelId,@Param("deviceId")String deviceId);
	
	@Select("select * from t_device_app where device_app_id=#{deviceAppId}")
	DeviceApp isActive(@Param("deviceAppId")String deviceAppId);
}
