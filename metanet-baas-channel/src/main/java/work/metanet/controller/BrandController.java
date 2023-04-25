package work.metanet.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import work.metanet.aop.ApiModule;
import work.metanet.aop.ApiModule.Module;
import work.metanet.aop.ApiPermission;
import work.metanet.aop.ApiPermission.AUTH;
import work.metanet.api.brand.IBrandService;
import work.metanet.api.brand.protocol.ReqBrandList;
import work.metanet.api.brand.protocol.ReqBrandList.RespBrandList;
import work.metanet.base.BaseController;
import work.metanet.base.RespPaging;
import work.metanet.base.Result;
import work.metanet.base.ResultMessage;

@ApiModule(module = Module.BRAND)
@Controller
@RequestMapping("brand")
public class BrandController extends BaseController{
	
	@DubboReference IBrandService brandService;
	
	@ApiPermission(AUTH.OPEN)
	@ResponseBody
	@RequestMapping("brandSelected")
	public Result<RespPaging<RespBrandList>> brandSelected(@RequestBody ReqBrandList req) throws Exception {
		return ResultMessage.SUCCESS.result(brandService.brandList(req));
	}
	
	
	
}
