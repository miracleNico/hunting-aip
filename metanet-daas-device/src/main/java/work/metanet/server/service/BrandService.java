package work.metanet.server.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import work.metanet.api.brand.IBrandService;
import work.metanet.api.brand.protocol.ReqBrandInfo;
import work.metanet.api.brand.protocol.ReqBrandInfo.RespBrandInfo;
import work.metanet.api.brand.protocol.ReqBrandList;
import work.metanet.api.brand.protocol.ReqBrandList.RespBrandList;
import work.metanet.api.brand.protocol.ReqRemoveBrand;
import work.metanet.api.brand.protocol.ReqSaveBrand;
import work.metanet.api.brand.protocol.ReqSaveBrand.RespSaveBrand;
import work.metanet.base.RespPaging;
import work.metanet.base.ResultMessage;
import work.metanet.constant.ConstSource;
import work.metanet.exception.DaasException;
import work.metanet.model.Brand;
import work.metanet.server.dao.BrandMapper;
import work.metanet.server.dao.SequenceMapper;

import cn.hutool.core.bean.BeanUtil;

@DubboService
public class BrandService implements IBrandService{

	@Autowired
	private BrandMapper brandMapper;
	@Autowired
	private SequenceMapper sequenceMapper;
	
	/**
	 * @Description: 品牌列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespPaging<RespBrandList> brandList(ReqBrandList req) throws Exception {
		RespPaging<RespBrandList> respPaging = new RespPaging<RespBrandList>();
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<RespBrandList> list = brandMapper.brandList(BeanUtil.beanToMap(req));
		BeanUtil.copyProperties(new PageInfo<RespBrandList>(list), respPaging);
		for (RespBrandList resp : respPaging.getList()) {
			resp.setSource(ConstSource.getText(resp.getSource()));
		}
		return respPaging;
	}

	/**
	 * @Description: 品牌信息
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespBrandInfo brandInfo(ReqBrandInfo req) throws Exception {
		RespBrandInfo resp = brandMapper.brandInfo(BeanUtil.beanToMap(req));
		if(resp!=null) {
			resp.setSource(ConstSource.getText(resp.getSource()));
		}
		return resp;
	}

	/**
	 * @Description: 保存品牌
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespSaveBrand saveBrand(ReqSaveBrand req) throws Exception {
		Brand brand = new Brand();
		BeanUtil.copyProperties(req, brand);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("brandName", req.getBrandName());
		Brand dbBrand = brandMapper.getBrand(map);
		
		if(StringUtils.isNotBlank(brand.getBrandId())) {
			//修改操作
			if(!BeanUtil.isEmpty(dbBrand) && !dbBrand.getBrandId().equals(brand.getBrandId()))
				throw DaasException.of().setMsg("名称已存在");
			if(BeanUtil.isEmpty(dbBrand) || dbBrand.getBrandId().equals(brand.getBrandId())) {
				brandMapper.updateByPrimaryKeySelective(brand);
			}else {
				throw DaasException.of().setResult(ResultMessage.FAILURE.result());
			}
		}else {
			//新增操作
			if(!BeanUtil.isEmpty(dbBrand))
				throw DaasException.of().setMsg("名称已存在");
			brand.setBrandId(sequenceMapper.generateBrandId());
			brandMapper.insertSelective(brand);
		}
		
		RespSaveBrand resp = new RespSaveBrand();
		BeanUtil.copyProperties(brand, resp);
		return resp;
	}

	/**
	 * @Description: 删除品牌
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public void removeBrand(List<ReqRemoveBrand> req) throws Exception {
		brandMapper.removeBrand(req);
	}
	
	
	
	
	
	
	
	
}
