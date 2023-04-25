
package work.metanet.api.vision;

import java.util.List;

import work.metanet.api.vision.protocol.ReqEyestrainInfo;
import work.metanet.api.vision.protocol.ReqEyestrainInfo.RespEyestrainInfo;
import work.metanet.api.vision.protocol.ReqEyestrainList;
import work.metanet.api.vision.protocol.ReqEyestrainList.RespEyestrainList;
import work.metanet.api.vision.protocol.ReqRemoveEyestrain;
import work.metanet.api.vision.protocol.ReqSaveEyestrain;
import work.metanet.api.vision.protocol.ReqSaveEyestrain.RespSaveEyestrain;
import work.metanet.base.RespPaging;

/**
 * @author EdisonFeng
 * @Description 眼疲劳记录（用眼活动），用于rpc服务
 * @DateTime 2021年5月14日
 * Copyright(c) 2021. All Rights Reserved
 */

public interface IEyestrain {
	/**
	 * @Description: 查询列表
	 * @Author Edison F.
	 * @DateTime 2021/05/14
	 */
	RespPaging<RespEyestrainList> getEyestrainList(ReqEyestrainList req) throws Exception;


	List<RespEyestrainList> getEyestrainListOrderByUseTypeAndGroupByTime(ReqEyestrainList req) throws Exception;

	/**
	 * @Description: 查询详情
	 * @Author Edison F.
	 * @DateTime 2021/05/14
	 */
	RespEyestrainInfo getEyestrainInfo(ReqEyestrainInfo req) throws Exception;
	
	/**
	 * @return 
	 * @Description: 保存信息
	 * @Author Edison F.
	 * @DateTime 2021/05/14
	 */
	RespSaveEyestrain updateEyestrain(ReqSaveEyestrain req) throws Exception;
	
	/**
	 * @Description: 增加信息
	 * @Author Edison F.
	 * @DateTime 2021/05/14
	 */
	RespSaveEyestrain insertEyestrain(ReqSaveEyestrain req) throws Exception;
	
	/**
	 * @Description: 删除信息
	 * @Author Edison F.
	 * @DateTime 2021/05/14
	 */
	String removeEyestrain(List<ReqRemoveEyestrain> tests) throws Exception;
	/**
	 * 根据UserId 获取信息列表
	 * @param userId
	 * @return 信息列表
	 */
	List<RespEyestrainInfo> eyestrainsInfoByUser(String userId);

	/**
	 * 根据UserId或者EyestrainId获取信息，由于一个user可拥有多个EyestrainInfo
	 * @param req
	 * @return 信息列表
	 */
	List<RespEyestrainInfo> getUserEyestrainInfo(ReqEyestrainInfo req);
}
