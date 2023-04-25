package work.metanet.vision.dao;

import java.util.List;
import java.util.Map;

import work.metanet.api.vision.protocol.*;
import org.apache.ibatis.annotations.Param;
import work.metanet.api.vision.protocol.ReqEyeInfo.RespEyeInfo;
import work.metanet.api.vision.protocol.ReqEyeList.RespEyeList;
import work.metanet.model.Eye;

import tk.mybatis.mapper.common.Mapper;

/**
 * @author Edison F.
 * @Description DAO 眼睛
 * @DateTime 2021年4月20日
 * Copyright(c) 2021. All Rights Reserved
 */
public interface EyeMapper extends Mapper<Eye> {
	/**
	 * @Description: 详情
	 * @Author Edison F.
	 * @DateTime 2021年4月20日
	 */
	RespEyeInfo getEyeInfo(Map<String, Object> map);
	
	/**
	 * @Description: 列表
	 * @Author Edison F.
	 * @DateTime 2021年4月20日
	 */
	List<RespEyeList> getEyeList(Map<String, Object> map);
	
	/**
	 * @Description: 删除
	 * @Author Edison F.
	 * @DateTime 2021年4月20日
	 */
	int removeEye(@Param("list")List<ReqRemoveEye> list);
	
	/**
	 * @Description: 插入信息
	 * @param req 保存数据请求
	 * @return 记录条数
	 */
	int insertEye(ReqSaveEye req);
	
	/**
	 * @Description: 修改信息
	 * @param req 保存数据请求
	 * @return 记录条数
	 */
	int updateEye(ReqSaveEye req);

	/**
	 * @Description: 通过用户ID查询信息
	 * @param userId 用户ID
	 * @return 信息列表
	 */
	List<RespEyeInfo> eyesInfoByUser(String userId);
}
