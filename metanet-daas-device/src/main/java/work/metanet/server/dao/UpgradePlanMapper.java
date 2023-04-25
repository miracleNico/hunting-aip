/**  
 * @Title: OrderMapper.java
 * @Description: TODO
 * @author wanbo
 * @date 2018年3月28日
 */
package work.metanet.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import work.metanet.api.upgradePlan.protocol.ReqRemoveUpgradePlan;
import work.metanet.api.upgradePlan.protocol.ReqUpgradePlanInfo.RespUpgradePlanInfo;
import work.metanet.api.upgradePlan.protocol.ReqUpgradePlanList.RespUpgradePlanList;
import work.metanet.api.upgradePlan.protocol.ReqUpgradePlanScopeInfo.RespUpgradePlanScopeInfo;
import work.metanet.model.UpgradePlan;
import work.metanet.model.UpgradePlanScope;

import tk.mybatis.mapper.common.Mapper;

public interface UpgradePlanMapper extends Mapper<UpgradePlan>{
	
	UpgradePlan getUpgradePlan(Map<String, Object> map);
	
	/**
	 * @Description: 升级计划详情
	 * @Author wanbo
	 * @DateTime 2019/11/25
	 */
	RespUpgradePlanInfo upgradePlanInfo(Map<String, Object> map);
	
	/**
	 * @Description: 升级计划列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	List<RespUpgradePlanList> upgradePlanList(Map<String, Object> map);
	
	/**
	 * @Description: 升级计划范围
	 * @Author wanbo
	 * @DateTime 2019/12/02
	 */
	List<RespUpgradePlanScopeInfo> upgradePlanScopeInfoList(String upgradePlanId);
	
	/**
	 * @Description: 删除升级计划
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	int removeUpgradePlan(@Param("list")List<ReqRemoveUpgradePlan> list);
	
	
	/**
	 * @Description: 发布或取消发布升级计划
	 * @Author wanbo
	 * @DateTime 2019/11/29
	 */
	@Update("update t_upgrade_plan set send_status=${sendStatus} where upgrade_plan_id=#{upgradePlanId}")
	int sendUpgradePlan(@Param("upgradePlanId")String upgradePlanId,@Param("sendStatus")Boolean sendStatus);
	
	/**
	 * @Description: 删除升级计划范围
	 * @Author wanbo
	 * @DateTime 2019/12/04
	 */
	@Delete("delete from t_upgrade_plan_scope where upgrade_plan_id=#{upgradePlanId}")
	int removeUpgradePlanScope(String upgradePlanId);
	
	/**
	 * @Description: 批量添加升级计划范围
	 * @Author wanbo
	 * @DateTime 2019/12/04
	 */
	int addUpgradePlanScope(@Param("list")List<UpgradePlanScope> list);
	
}
