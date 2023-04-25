package work.metanet.api.version;

import java.util.List;

import work.metanet.api.version.protocol.ReqAppVersionInfo;
import work.metanet.api.version.protocol.ReqAppVersionInfo.RespAppVersionInfo;
import work.metanet.api.version.protocol.ReqAppVersionList;
import work.metanet.api.version.protocol.ReqAppVersionList.RespAppVersionList;
import work.metanet.api.version.protocol.ReqRemoveAppVersion;
import work.metanet.api.version.protocol.ReqSaveAppVersion;
import work.metanet.api.version.protocol.ReqSaveAppVersion.RespSaveAppVersion;
import work.metanet.api.version.vo.AppVersionVo;
import work.metanet.base.RespPaging;

public interface IAppVersionService {
	
	AppVersionVo getAppVersion(String packageName,String versionCode)throws Exception;

	/**
	 * @Description: 版本列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespPaging<RespAppVersionList> appVersionList(ReqAppVersionList req) throws Exception;
	
	/**
	 * @Description: 版本详情
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespAppVersionInfo appVersionInfo(ReqAppVersionInfo req) throws Exception;
	
	/**
	 * @Description: 保存版本
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespSaveAppVersion saveAppVersion(ReqSaveAppVersion req) throws Exception;
	
	/**
	 * @Description: 删除版本
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void removeAppVersion(List<ReqRemoveAppVersion> req) throws Exception;
	
	
}
