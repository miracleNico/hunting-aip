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

import work.metanet.api.brand.protocol.ReqBrandInfo.RespBrandInfo;
import work.metanet.api.brand.protocol.ReqBrandList.RespBrandList;
import work.metanet.api.brand.protocol.ReqRemoveBrand;
import work.metanet.model.Brand;

import tk.mybatis.mapper.common.Mapper;

public interface BrandMapper extends Mapper<Brand>{
	
	Brand getBrand(Map<String, Object> map);
	
	/**
	 * @Description: 品牌详情
	 * @Author wanbo
	 * @DateTime 2019/11/25
	 */
	RespBrandInfo brandInfo(Map<String, Object> map);
	
	/**
	 * @Description: 品牌列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	List<RespBrandList> brandList(Map<String, Object> map);
	
	/**
	 * @Description: 删除品牌
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	int removeBrand(@Param("list")List<ReqRemoveBrand> list);
	
}
