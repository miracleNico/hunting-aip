package work.metanet.api.model;

import java.util.List;

import work.metanet.api.model.protocol.ReqModelInfo;
import work.metanet.api.model.protocol.ReqModelInfo.RespModelInfo;
import work.metanet.api.model.protocol.ReqModelList;
import work.metanet.api.model.protocol.ReqRemoveModel;
import work.metanet.api.model.protocol.ReqModelList.RespModelList;
import work.metanet.api.model.protocol.ReqSaveModel;
import work.metanet.api.model.protocol.ReqSaveModel.RespSaveModel;
import work.metanet.base.RespPaging;

public interface IModelService {

	/**
	 * @Description: 型号列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespPaging<RespModelList> modelList(ReqModelList req) throws Exception;
	
	/**
	 * @Description: 型号详情
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespModelInfo modelInfo(ReqModelInfo req) throws Exception;
	
	/**
	 * @Description: 保存型号
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespSaveModel saveModel(ReqSaveModel req) throws Exception;
	
	/**
	 * @Description: 删除型号
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void removeModel(List<ReqRemoveModel> req) throws Exception;

	
}
