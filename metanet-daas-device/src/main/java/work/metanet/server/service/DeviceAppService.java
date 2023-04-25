package work.metanet.server.service;


import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import work.metanet.api.deviceApp.IDeviceAppService;
import work.metanet.constant.ConstCacheKey;
import work.metanet.exception.DaasException;
import work.metanet.model.DeviceApp;
import work.metanet.server.dao.DeviceAppMapper;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;

@DubboService
public class DeviceAppService implements IDeviceAppService{
	
	@Autowired
	private DeviceAppMapper deviceAppMapper;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Override
	public boolean isActivate(String deviceAppId) throws Exception{
		Boolean isActive = false;
		String cacheKey = ConstCacheKey.DEVICE_APP_ACTIVE.cacheKey();
		if(stringRedisTemplate.hasKey(cacheKey) && stringRedisTemplate.opsForHash().hasKey(cacheKey, deviceAppId)) {
			isActive = true;
		}else {
			DeviceApp deviceApp = deviceAppMapper.isActive(deviceAppId);
			if(deviceApp!=null && deviceApp.getActivateTime()!=null) {
				stringRedisTemplate.opsForHash().put(cacheKey, deviceAppId, JSONUtil.toJsonStr(deviceApp));
				isActive = true;
			}
		}
		return isActive;
	}
	
	@Override
	public String getDeviceAppId(String deviceId, String packageName) throws Exception {
		DeviceApp deviceApp = deviceAppMapper.getDeviceApp(MapUtil.builder().put("deviceId", deviceId).put("packageName", packageName).build());
		if(deviceApp==null) throw DaasException.of().setMsg("设备产品信息错误");
		return deviceApp.getDeviceAppId();
	}
	
	
	
}
