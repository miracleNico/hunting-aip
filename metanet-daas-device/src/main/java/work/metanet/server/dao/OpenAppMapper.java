package work.metanet.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import work.metanet.api.openApp.protocol.ReqOpenAppList;
import work.metanet.api.openApp.protocol.ReqOpenAppList.RespOpenAppList;
import work.metanet.api.openApp.protocol.ReqOpenAppRemove;
import work.metanet.model.OpenApp;

import tk.mybatis.mapper.common.Mapper;

public interface OpenAppMapper extends Mapper<OpenApp>{

	List<OpenApp> appSelected(Map<String, Object> map);
	
	List<RespOpenAppList> appList(ReqOpenAppList req);
	
	int removeApp(@Param("list")List<ReqOpenAppRemove> list);
}
