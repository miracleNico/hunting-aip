package work.metanet.api.statistical.protocol;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
public class ReqStatistical implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Accessors(chain = true)
	@Data
	public static class RespStatistical implements Serializable{
		private static final long serialVersionUID = 1L;
		private Integer userTotal;
		private Integer yesterdayActiveUserTotal;
		private Integer deviceTotal;
		private Integer deviceActiveTotal;
		
	}
	
}
