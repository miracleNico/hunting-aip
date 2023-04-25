package work.metanet.api.storeApp;

import java.util.List;

import work.metanet.api.storeApp.protocol.ReqRemoveStoreApp;
import work.metanet.api.storeApp.protocol.ReqSaveStoreApp;
import work.metanet.api.storeApp.protocol.ReqSearchStoreApp;
import work.metanet.api.storeApp.protocol.ReqSearchStoreApp.RespSearchStoreApp;
import work.metanet.api.storeApp.protocol.ReqStoreAppInfo;
import work.metanet.api.storeApp.protocol.ReqStoreAppInfo.RespStoreAppInfo;
import work.metanet.api.storeApp.protocol.ReqStoreAppList;
import work.metanet.api.storeApp.protocol.ReqStoreAppList.RespStoreAppList;
import work.metanet.base.RespPaging;

public interface IStoreAppService {
	
	/**
	 * @Description: 应用商店搜索
	 * @Author wanbo
	 * @DateTime 2020/05/26
	 */
	RespPaging<RespSearchStoreApp> searchStoreApp(ReqSearchStoreApp req) throws Exception;

	/**
	 * @Description: 应用商店列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespPaging<RespStoreAppList> storeAppList(ReqStoreAppList req) throws Exception;
	
	/**
	 * @Description: 应用商店详情
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespStoreAppInfo storeAppInfo(ReqStoreAppInfo req) throws Exception;
	
	/**
	 * @Description: 保存应用商店
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void saveStoreApp(ReqSaveStoreApp req) throws Exception;
	
	/**
	 * @Description: 删除应用商店
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void removeStoreApp(List<ReqRemoveStoreApp> req) throws Exception;
	
	
	
	
}
