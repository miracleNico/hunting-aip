package work.metanet.api.brand;

import java.util.List;

import work.metanet.api.brand.protocol.ReqBrandInfo;
import work.metanet.api.brand.protocol.ReqBrandInfo.RespBrandInfo;
import work.metanet.api.brand.protocol.ReqBrandList;
import work.metanet.api.brand.protocol.ReqBrandList.RespBrandList;
import work.metanet.api.brand.protocol.ReqRemoveBrand;
import work.metanet.api.brand.protocol.ReqSaveBrand;
import work.metanet.api.brand.protocol.ReqSaveBrand.RespSaveBrand;
import work.metanet.base.RespPaging;

public interface IBrandService {

	/**
	 * @Description: 品牌列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespPaging<RespBrandList> brandList(ReqBrandList req) throws Exception;
	
	/**
	 * @Description: 品牌详情
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespBrandInfo brandInfo(ReqBrandInfo req) throws Exception;
	
	/**
	 * @Description: 保存品牌
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespSaveBrand saveBrand(ReqSaveBrand req) throws Exception;
	
	/**
	 * @Description: 删除品牌
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void removeBrand(List<ReqRemoveBrand> req) throws Exception;
	
	
}
