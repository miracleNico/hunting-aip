package work.metanet.api.statistical.protocol;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import work.metanet.api.statistical.vo.ActiveUserAppVo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "请求-统计日活用户")
public class ReqDayActiveUser implements Serializable{
	private static final long serialVersionUID = -5232524211801154192L;
	
	
	@Data
	@ApiModel(value = "响应-统计日活用户")
	public static class RespDayActiveUser implements Serializable{
		private static final long serialVersionUID = 6508006755407317012L;
		private List<ActiveUserAppVo> activeUserApps;
		private List<String> dates = new ArrayList<String>();
		
	}
	
}
