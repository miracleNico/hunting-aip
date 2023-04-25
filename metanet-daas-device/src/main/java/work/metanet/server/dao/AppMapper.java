/**  
 * @Title: OrderMapper.java
 * @Description: TODO
 * @author wanbo
 * @date 2018年3月28日
 */
package work.metanet.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import work.metanet.api.app.protocol.ReqAppInfo.RespAppInfo;
import work.metanet.api.app.protocol.ReqAppList.RespAppList;
import work.metanet.api.app.protocol.ReqRemoveApp;
import work.metanet.api.app.vo.AppVo;
import work.metanet.api.upgradePlan.protocol.ReqUpgrade.RespUpgrade;
import work.metanet.model.App;

import tk.mybatis.mapper.common.Mapper;

public interface AppMapper extends Mapper<App>{
	
	List<AppVo> appVoList(@Param("channelId")String channelId);
	
	App getApp(Map<String, String> map);
	
	/**
	 * @Description: 产品详情
	 * @Author wanbo
	 * @DateTime 2019/11/25
	 */
	RespAppInfo appInfo(Map<String, Object> map);
	
	/**
	 * @Description: 产品列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	List<RespAppList> appList(Map<String, Object> map);
	
	/**
	 * @Description: 删除产品
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	int removeApp(@Param("list")List<ReqRemoveApp> list);
	
	/**
	 * @Description: 升级
	 * @Author wanbo
	 * @DateTime 2019/12/04
	 */
	RespUpgrade upgrade(@Param("deviceId")String deviceId,@Param("brandName")String brandName,@Param("modelName")String modelName, @Param("versionCode")String versionCode, @Param("packageName")String packageName);
	
	
}
