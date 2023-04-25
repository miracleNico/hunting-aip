package work.metanet.api.statistical;

import work.metanet.api.statistical.protocol.ReqDayActiveUser.RespDayActiveUser;
import work.metanet.api.statistical.protocol.ReqStatistical.RespStatistical;

public interface IStatisticalService {
	
	RespStatistical statistical(String channelId) throws Exception;
	
	/**
	 * @Description: 统计日活用户
	 * @Author wanbo
	 * @DateTime 2020/05/08
	 */
	RespDayActiveUser dayActiveUser(String channelId) throws Exception;
	
}
