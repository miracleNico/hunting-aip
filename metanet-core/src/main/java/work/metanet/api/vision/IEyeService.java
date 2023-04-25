package work.metanet.api.vision;

import java.util.List;

import work.metanet.api.vision.protocol.ReqEyeInfo;
import work.metanet.api.vision.protocol.ReqEyeInfo.RespEyeInfo;
import work.metanet.api.vision.protocol.ReqEyeList;
import work.metanet.api.vision.protocol.ReqRemoveEye;
import work.metanet.api.vision.protocol.ReqSaveEye;
import work.metanet.api.vision.protocol.ReqSaveVisionTest;
import work.metanet.api.vision.protocol.ReqSaveEye.RespSaveEye;
import work.metanet.api.vision.protocol.ReqEyeList.RespEyeList;
import work.metanet.base.RespPaging;

/**
 * @author Edison F.
 * @Description 用户眼睛相关操作
 * @DateTime 2021/04/20
 */
public interface IEyeService {
	/**
	 * @Description: 查询眼睛列表
	 * @Author Edison F.
	 * @DateTime 2021/04/20
	 */
	RespPaging<RespEyeList> getEyeList(ReqEyeList req) throws Exception;
	
	/**
	 * @Description: 查询眼睛详情
	 * @Author Edison F.
	 * @DateTime 2021/04/20
	 */
	RespEyeInfo getEyeInfo(ReqEyeInfo req) throws Exception;
	
	/**
	 * @return 
	 * @Description: 增加、修改眼睛信息
	 * @Author Edison F.
	 * @DateTime 2021/04/20
	 */
	RespSaveEye saveEye(ReqSaveEye req) throws Exception;

	/**
	 * @Description: 增加眼睛信息
	 * @Author Edison F.
	 * @DateTime 2021/05/03
	 */
	RespSaveEye insertEye(ReqSaveEye req) throws Exception;

	/**
	 * @Description: 删除眼睛信息
	 * @Author Edison F.
	 * @DateTime 2021/04/20
	 */
	String removeEye(List<ReqRemoveEye> eyes) throws Exception;


	/**
	 * 根据UserId 获取两只眼睛
	 * @param userId
	 * @return
	 */
	List<RespEyeInfo> eyesInfoByUser(String userId);
	
}
