package work.metanet.controller;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import work.metanet.aop.ApiModule;
import work.metanet.aop.ApiModule.Module;
import work.metanet.aop.ApiPermission;
import work.metanet.aop.ApiPermission.AUTH;
import work.metanet.api.model.IModelService;
import work.metanet.api.model.protocol.ReqBrandModelList;
import work.metanet.api.model.protocol.ReqModelList.RespModelList;
import work.metanet.base.BaseController;
import work.metanet.base.RespPaging;
import work.metanet.base.Result;
import work.metanet.base.ResultMessage;

@ApiModule(module = Module.MODEL)
@Controller
@RequestMapping("model")
public class ModelController extends BaseController{
	
	@DubboReference IModelService modelService;
	
	@ApiPermission(AUTH.OPEN)
	@ResponseBody
	@RequestMapping("brandModelSelected")
	public Result<RespPaging<RespModelList>> brandModelSelected(@Valid @RequestBody ReqBrandModelList req) throws Exception {
		if(StringUtils.isBlank(req.getBrand())) 
			return ResultMessage.SUCCESS.result(new RespPaging<RespModelList>());
		return ResultMessage.SUCCESS.result(modelService.modelList(req));
	}
	
	
}
