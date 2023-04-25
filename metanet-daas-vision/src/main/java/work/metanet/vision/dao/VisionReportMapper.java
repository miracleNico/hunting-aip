
package work.metanet.vision.dao;

import java.util.List;
import java.util.Map;

import work.metanet.api.vision.protocol.*;
import org.apache.ibatis.annotations.Param;

import work.metanet.api.vision.protocol.ReqRecommendVisionReport.RespRecommendReportList;
import work.metanet.api.vision.protocol.ReqSearchVisionReport.RespSearchReportList;
import work.metanet.api.vision.protocol.ReqVisionReportInfo.RespVisionReportInfo;
import work.metanet.api.vision.protocol.ReqVisionReportList.RespVisionReportList;
import work.metanet.model.VisionReport;

import tk.mybatis.mapper.common.Mapper;

/**
 * @Description DAO:视力报告
 * @author EdisonFeng
 * @DateTime 2021年4月21日
 * Copyright(c) 2021. All Rights Reserved
 */
public interface VisionReportMapper extends Mapper<VisionReport> {
	/**
	 * @Description: 视力报告详情
	 * @Author Edison F.
	 * @DateTime 2021年4月20日
	 */
	RespVisionReportInfo getVisionReportInfo(Map<String, Object> map);
	
	/**
	 * @Description: 视力报告列表
	 * @Author Edison F.
	 * @DateTime 2021年4月20日
	 */
	List<RespVisionReportList> getVisionReportList(Map<String, Object> map);

	List<RespVisionReportList> getTwelveVisionReport(Map<String, Object> map);
	
	/**
	 * @Description: 删除视力报告
	 * @Author Edison F.
	 * @DateTime 2021年4月20日
	 */
	String removeVisionReport(@Param("list")List<ReqRemoveVisionReport> list);
	
	/**
	 * @Description: 搜索视力报告
	 */
	List<RespSearchReportList> searchVisionReport(ReqSearchVisionReport req);
	
	/**
	 * @Description: 推荐视力报告
	 */
	List<RespRecommendReportList> recommendVisionReport(ReqRecommendVisionReport req);

	/**
	 * @Description: 插入视力测试报告
	 * @param ReqSaveVisionReport req 保存数据请求
	 * @return 记录条数
	 */
	int insertVisionReport(ReqSaveVisionReport req);
	
	/**
	 * @Description: 修改视力测试报告
	 * @param ReqSaveVisionReport req 保存数据请求
	 * @return 记录条数
	 */
	int updateVisionReport(ReqSaveVisionReport req);


	List<ReqVisionAVG.RespVisionAVG> getVisionAVG(ReqVisionAVG req);
}
