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

import work.metanet.api.model.protocol.ReqModelInfo.RespModelInfo;
import work.metanet.api.model.protocol.ReqModelList.RespModelList;
import work.metanet.api.model.protocol.ReqRemoveModel;
import work.metanet.model.Model;

import tk.mybatis.mapper.common.Mapper;

public interface ModelMapper extends Mapper<Model>{
	
	Model getModel(Map<String, Object> map);
	
	/**
	 * @Description: 型号详情
	 * @Author wanbo
	 * @DateTime 2019/11/25
	 */
	RespModelInfo modelInfo(Map<String, Object> map);
	
	/**
	 * @Description: 型号列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	List<RespModelList> modelList(Map<String, Object> map);
	
	/**
	 * @Description: 删除型号
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	int removeModel(@Param("list")List<ReqRemoveModel> list);
	
}
