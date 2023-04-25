package work.metanet.base;

import work.metanet.utils.HttpServletRequestUtil;
import work.metanet.utils.token.TokenUtil;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;

public class BaseController {
	
	public UserToken getUser() throws Exception{
		String authorization = HttpServletRequestUtil.getRequest().getHeader("Authorization");
		if(StrUtil.isBlank(authorization)) return new UserToken();
		return JSONUtil.toBean(TokenUtil.getPayloadSub(authorization), UserToken.class);
	}
	
	public String getUserId() throws Exception{
		return getUser().getUserId();
	}
	
	public String getUserName() throws Exception{
		return getUser().getUserName();
	}
	
	public String getPackageName() {
		return HttpServletRequestUtil.getRequest().getHeader("packageName");
	}
	
	public String getVersionCode() {
		return HttpServletRequestUtil.getRequest().getHeader("versionCode");
	}
	
	public String getDeviceAppId() throws Exception {
		String cid = HttpServletRequestUtil.getRequest().getHeader("cid");
		if(StrUtil.isBlank(cid)) {
			cid = getUser().getCid();
		}
		return cid;
	}
	
	public String getDeviceId() throws Exception {
		String deviceId = HttpServletRequestUtil.getRequest().getHeader("deviceId");
		if(StrUtil.isBlank(deviceId)) {
			deviceId = getUser().getDeviceId();
		}
		return deviceId;
	}
	
	public String getAppId() throws Exception {
		String appId = HttpServletRequestUtil.getRequest().getHeader("appId");
		if(StrUtil.isBlank(appId)) {
			appId = getUser().getAppId();
		}
		return appId;
	}
}
