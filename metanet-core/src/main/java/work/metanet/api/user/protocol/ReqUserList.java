package work.metanet.api.user.protocol;

import java.io.Serializable;

import work.metanet.api.user.protocol.ReqUserInfo.RespUserInfo;
import work.metanet.base.Paging;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
@Data
public class ReqUserList extends Paging implements Serializable{
	
	private static final long serialVersionUID = -3851499588497776595L;
	
	//like id|phone
    private String user;
    //like id|name|mac|uuid
    private String device;
    private String channelId;
    private String appId;
    private Boolean enableStatus;
    private String startTime;
	private String endTime;
    private String remark;
	
	
	@EqualsAndHashCode(callSuper=true)
	@Data
	public static class RespUserList extends RespUserInfo implements Serializable{
		
		private static final long serialVersionUID = -5255740365569977577L;
		private String appName;
	    private String lastLoginDeviceName;
	    private String lastLoginDeviceMac;
	    private String lastLoginTime;
	}
}
