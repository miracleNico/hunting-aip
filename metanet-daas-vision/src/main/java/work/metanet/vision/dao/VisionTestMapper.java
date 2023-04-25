package work.metanet.vision.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import work.metanet.api.vision.protocol.ReqRemoveVisionTest;
import work.metanet.api.vision.protocol.ReqSaveVisionReport;
import work.metanet.api.vision.protocol.ReqSaveVisionTest;
import work.metanet.api.vision.protocol.ReqVisionCondition.RespVisionCondition;
import work.metanet.api.vision.protocol.ReqVisionTestInfo.RespVisionTestInfo;
import work.metanet.api.vision.protocol.ReqVisionTestList.RespVisionTestList;
import work.metanet.model.VisionTest;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Description DAO 视力测试活动
 * @author Edison F.
 * @DateTime 2021年4月20日
 * Copyright(c) 2021. All Rights Reserved
 */
public interface VisionTestMapper extends Mapper<VisionTest> {
	/**
	 * @Description: 视力测试活动详情
	 * @Author Edison F.
	 * @DateTime 2021年4月20日
	 */
	RespVisionTestInfo getVisionTestInfo(Map<String, Object> map);
	
	/**
	 * @Description: 视力测试活动列表
	 * @Author Edison F.
	 * @DateTime 2021年4月20日
	 */
	List<RespVisionTestList> getVisionTestList(Map<String, Object> map);
	
	/**
	 * @Description: 删除视力测试活动
	 * @Author Edison F.
	 * @DateTime 2021年4月20日
	 */
	String removeVisionTest(@Param("list")List<ReqRemoveVisionTest> list);
	
	/**
	 * @Description: 视力筛选条件
	 * @Author Edison F.
	 * @DateTime 2021年4月25日
	 */
	List<RespVisionCondition> getVisionCondition(Map<String, Object> map);
	
	/**
	 * @Description: 插入视力测试活动
	 * @param ReqSaveVisionTest req 保存数据请求
	 * @return 记录条数
	 */
	int insertVisionTest(ReqSaveVisionTest req);
	
	/**
	 * @Description: 修改视力测试活动
	 * @param ReqSaveVisionTest req 保存数据请求
	 * @return 记录条数
	 */
	int updateVisionTest(ReqSaveVisionTest req);
}
