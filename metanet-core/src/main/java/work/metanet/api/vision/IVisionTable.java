package work.metanet.api.vision;

import work.metanet.api.vision.protocol.ReqVisionTableInfo;
import work.metanet.api.vision.protocol.ReqVisionTableInfo.RespVisionTableInfo;
import work.metanet.api.vision.protocol.ReqVisionTestInfo;
import work.metanet.api.vision.protocol.ReqVisionTestInfo.RespVisionTestInfo;

/**
 * @author Edison F.
 * @Description 视力表
 * @DateTime 2021/05/1
 */
public interface IVisionTable {
	/**
	 * @Description: 查询视力表
	 * @Author Edison F.
	 * @DateTime 2021/05/1
	 */
	RespVisionTableInfo getVisionTableInfo(ReqVisionTableInfo req) throws Exception;

}
