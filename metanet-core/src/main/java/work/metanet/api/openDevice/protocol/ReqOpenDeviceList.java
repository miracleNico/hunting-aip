package work.metanet.api.openDevice.protocol;

import java.io.Serializable;

import work.metanet.base.Paging;
import work.metanet.model.OpenDevice;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@ApiModel("请求-开放平台设备列表")
@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
@Data
public class ReqOpenDeviceList extends Paging  implements Serializable{
	private static final long serialVersionUID = 1L;
	private String channelId;
	private String appId;
	private String deviceId;
	private String mac;
	private String sn;
	private Boolean enable;
	private String remark;
	private String startTime;
	private String endTime;
	
	@ApiModel("响应-开放平台设备列表")
	@EqualsAndHashCode(callSuper=true)
	@Accessors(chain = true)
	@Data
	public static class RespOpenDeviceList extends OpenDevice implements Serializable {
		private static final long serialVersionUID = 1L;
		private String channelName;
		private String appName;
	}

}
