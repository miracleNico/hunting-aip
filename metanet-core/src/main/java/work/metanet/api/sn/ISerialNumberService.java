package work.metanet.api.sn;

import java.util.List;

import work.metanet.api.sn.protocol.ReqRemoveSerialNumber;
import work.metanet.api.sn.protocol.ReqSaveSerialNumber;
import work.metanet.api.sn.protocol.ReqSerialNumberInfo;
import work.metanet.api.sn.protocol.ReqSerialNumberInfo.RespSerialNumberInfo;
import work.metanet.api.sn.protocol.ReqSerialNumberList;
import work.metanet.api.sn.protocol.ReqSerialNumberList.RespSerialNumberList;
import work.metanet.base.RespPaging;

public interface ISerialNumberService {

	/**
	 * @Description: SN码列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespPaging<RespSerialNumberList> serialNumberList(ReqSerialNumberList req) throws Exception;
	
	/**
	 * @Description: SN码详情
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespSerialNumberInfo serialNumberInfo(ReqSerialNumberInfo req) throws Exception;
	
	/**
	 * @Description: 保存SN码
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void saveSerialNumber(ReqSaveSerialNumber req) throws Exception;
	
	/**
	 * @Description: 删除SN码
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void removeSerialNumber(List<ReqRemoveSerialNumber> req) throws Exception;
	
	
}
