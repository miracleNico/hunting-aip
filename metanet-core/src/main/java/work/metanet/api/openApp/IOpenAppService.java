package work.metanet.api.openApp;

import java.util.List;
import java.util.Map;

import work.metanet.api.openApp.protocol.ReqOpenAppList;
import work.metanet.api.openApp.protocol.ReqOpenAppList.RespOpenAppList;
import work.metanet.api.openApp.protocol.ReqOpenAppRemove;
import work.metanet.api.openApp.protocol.ReqOpenAppSave;
import work.metanet.base.RespPaging;
import work.metanet.model.OpenApp;

public interface IOpenAppService {
	
	OpenApp getOpenAppByName(String channelId,String appName) throws Exception;

	OpenApp openApp(String appId) throws Exception;
	
	List<OpenApp> appSelected(Map<String, Object> map) throws Exception;;
	
	RespPaging<RespOpenAppList> appList(ReqOpenAppList req) throws Exception;
	
	OpenApp info(String appId) throws Exception;
	
	void saveApp(ReqOpenAppSave req) throws Exception;
	
	void removeApp(List<ReqOpenAppRemove> req) throws Exception;
}
