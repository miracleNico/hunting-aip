package work.metanet.api.log.protocol;

import java.io.Serializable;

import work.metanet.base.Paging;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
@Data
public class ReqOperLogList extends Paging implements Serializable{
	
	private static final long serialVersionUID = -8407139902687893300L;
	
	private String traceId;
	private String userName;
	private String appName;
	private String startTime;
	private String endTime;
	private String uri;
	private String ip;
	private String body;
	private String header;
	private String resp;
	
	@EqualsAndHashCode(callSuper=true)
	@Data
	public static class RespOperLogList extends ReqSaveOperLog implements Serializable{
		
		private static final long serialVersionUID = -2678071282452734327L;
		
	}

}
