
package work.metanet.vision.dao;

import java.util.List;
import java.util.Map;

import work.metanet.api.vision.protocol.ReqEyestrainInfo;
import org.apache.ibatis.annotations.Param;

import work.metanet.api.vision.protocol.ReqRemoveEyestrain;
import work.metanet.api.vision.protocol.ReqSaveEyestrain;
import work.metanet.api.vision.protocol.ReqEyestrainInfo.RespEyestrainInfo;
import work.metanet.api.vision.protocol.ReqEyestrainList.RespEyestrainList;
import work.metanet.model.Eyestrain;

import tk.mybatis.mapper.common.Mapper;
/**
 * @Description DAO映射类：用眼活动
 * @author EdisonFeng
 * @DateTime 2021年5月17日
 * Copyright(c) 2021. All Rights Reserved
 */
public interface EyestrainMapper extends Mapper<Eyestrain> {
	/**
	 * @Description: 详情
	 * @Author Edison F.
	 * @DateTime 2021年5月17日
	 */
	RespEyestrainInfo getEyestrainInfo(Map<String, Object> map);
	
	/**
	 * @Description: 列表
	 * @Author Edison F.
	 * @DateTime 2021年5月17日
	 */
	List<RespEyestrainList> getEyestrainList(Map<String, Object> map);

	/**
	 * @Description: 列表
	 * @Author Edison F.
	 * @DateTime 2021年5月17日
	 */
	List<RespEyestrainList> getEyestrainListOrderByUseTypeAndGroupByTime(Map<String, Object> map);
	
	/**
	 * @Description: 删除
	 * @Author Edison F.
	 */
	int removeEyestrain(@Param("list")List<ReqRemoveEyestrain> list);
	
	/**
	 * @Description: 插入信息
	 * @param req 保存数据请求
	 * @return 记录条数
	 */
	int insertEyestrain(ReqSaveEyestrain req);
	
	/**
	 * @Description: 修改信息
	 * @param req 保存数据请求
	 * @return 记录条数
	 */
	int updateEyestrain(ReqSaveEyestrain req);

	/**
	 * @Description: 通过用户ID查询信息
	 * @param userId 用户ID
	 * @return 信息列表
	 */
	List<RespEyestrainInfo> eyestrainsInfoByUser(String userId);

	/**
	 * @Description 通过用户id或EyestrainId查询信息列表
	 * @param req
	 * @return
	 */
	List<RespEyestrainInfo> getUserEyestrainInfo(ReqEyestrainInfo req);
}
