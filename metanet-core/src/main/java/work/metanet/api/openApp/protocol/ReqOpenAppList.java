package work.metanet.api.openApp.protocol;

import java.io.Serializable;

import work.metanet.base.Paging;
import work.metanet.model.OpenApp;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@ApiModel("请求-开放平台应用列表")
@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
@Data
public class ReqOpenAppList extends Paging implements Serializable{
	private static final long serialVersionUID = 1L;
	private String channelId;
	private String appName;
	private String remark;
	private String startTime;
	private String endTime;
	
	@ApiModel("响应-开放平台应用列表")
	@EqualsAndHashCode(callSuper=true)
	@Accessors(chain = true)
	@Data
	public static class RespOpenAppList extends OpenApp implements Serializable {
		private static final long serialVersionUID = 1L;
		private String channelName;
	}

}
