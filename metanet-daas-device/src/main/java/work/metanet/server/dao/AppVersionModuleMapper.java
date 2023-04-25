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
import org.apache.ibatis.annotations.Update;

import work.metanet.api.versionModule.protocol.ReqAppVersionModuleInfo.RespAppVersionModuleInfo;
import work.metanet.api.versionModule.protocol.ReqAppVersionModuleList.RespAppVersionModuleList;
import work.metanet.api.versionModule.protocol.ReqRemoveAppVersionModule;
import work.metanet.api.versionModule.protocol.ReqViewAppVersionModule.RespViewAppVersionModule;
import work.metanet.model.AppVersionModule;

import tk.mybatis.mapper.common.Mapper;

public interface AppVersionModuleMapper extends Mapper<AppVersionModule>{
	
	/**
	 * @Description: 产品模块展示
	 * @Author wanbo
	 * @DateTime 2020/03/06
	 */
	RespViewAppVersionModule viewAppVersionModule(@Param("packageName")String packageName,@Param("versionCode")String versionCode);
	
	AppVersionModule getAppVersionModule(Map<String, Object> map);
	
	/**
	 * @Description: 版本模块详情
	 * @Author wanbo
	 * @DateTime 2019/11/25
	 */
	RespAppVersionModuleInfo appVersionModuleInfo(Map<String, Object> map);
	
	/**
	 * @Description: 版本模块列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	List<RespAppVersionModuleList> appVersionModuleList(Map<String, Object> map);
	
	/**
	 * @Description: 删除版本模块
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	int removeAppVersionModule(@Param("list")List<ReqRemoveAppVersionModule> list);
	
	/**
	 * @Description: 修改父级节点
	 * @Author wanbo
	 * @DateTime 2020/02/27
	 */
	@Update("update t_app_version_module set parent_id=#{parentId} where version_module_id=#{versionModuleId}")
	int updAppVersionModuleParent(@Param("versionModuleId")String versionModuleId,@Param("parentId")String parentId);

	
}
