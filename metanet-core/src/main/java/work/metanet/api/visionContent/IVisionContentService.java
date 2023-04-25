package work.metanet.api.visionContent;

import work.metanet.api.visionContent.protocol.*;
import work.metanet.base.RespPaging;

import java.util.List;

public interface IVisionContentService {
	
	/**
	 * @Description: 视力内容
	 * @Author wanbo
	 * @DateTime 2020/07/24
	 */
	RespPaging<ReqVisionContentList.RespVisionContentList> visionContentList(ReqVisionContentList req) throws Exception;
	
	List<ReqRemoveVisionContent.RespRemoveVisionContent> removeVisionContents(List<ReqRemoveVisionContent> req) throws Exception;
	
	ReqVisionContentInfo.RespVisionContentInfo visionContentInfo(ReqVisionContentInfo req) throws Exception;
	
	ReqUpdateVisionContent.RespUpdateVisionContent updateVisionContent(ReqUpdateVisionContent req) throws Exception;

	ReqVisionContentInfo.RespVisionContentInfo insertVisionContent(ReqVisionContentInfo req)throws Exception;

	ReqVisionContentInfo.RespVisionContentInfo ranContent(List<ReqVisionContentInfo> infos) throws Exception;

	List<ReqVisionContentInfo.RespVisionContentInfo> getExerciseType() throws Exception;

	int getAllCount(List<String> ids)throws Exception;

}
