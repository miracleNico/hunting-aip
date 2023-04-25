package work.metanet.api.log;

import work.metanet.api.log.protocol.ReqOperLogList;
import work.metanet.api.log.protocol.ReqOperLogList.RespOperLogList;
import work.metanet.api.log.protocol.ReqSaveOperLog;
import work.metanet.base.RespPaging;

public interface ISystemLogService {
	
	void saveOperLog(ReqSaveOperLog req) throws Exception;
	
	/**
	 * @Description: 操作日志列表
	 * @Author wanbo
	 * @DateTime 2019/12/09
	 */
	RespPaging<RespOperLogList> operLogList(ReqOperLogList req) throws Exception;
	
}
