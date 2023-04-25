/**  
 * @Title: OrderMfeedbacker.java
 * @Description: TODO
 * @author wanbo
 * @date 2018年3月28日
 */
package work.metanet.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import work.metanet.api.feedback.protocol.ReqFeedbackInfo.RespFeedbackInfo;
import work.metanet.api.feedback.protocol.ReqFeedbackList.RespFeedbackList;
import work.metanet.api.feedback.protocol.ReqFeedbackInfo;
import work.metanet.api.feedback.protocol.ReqRemoveFeedback;
import work.metanet.model.Feedback;

import tk.mybatis.mapper.common.Mapper;

public interface FeedbackMapper extends Mapper<Feedback>{
	
	/**
	 * @Description: 获取反馈信息
	 * @Author wanbo
	 * @DateTime 2020/03/12
	 */
	@Select("select * from t_feedback where status=true and feedback_id=#{feedbackId}")
	RespFeedbackInfo feedbackInfo(ReqFeedbackInfo req);
	
	/**
	 * @Description: 反馈列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	List<RespFeedbackList> feedbackList(Map<String, Object> map);
	
	/**
	 * @Description: 删除反馈
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	int removeFeedback(@Param("list")List<ReqRemoveFeedback> list);
	
}
