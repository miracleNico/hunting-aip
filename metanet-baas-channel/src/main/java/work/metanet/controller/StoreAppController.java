package work.metanet.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import work.metanet.aop.ApiModule;
import work.metanet.aop.ApiModule.Module;
import work.metanet.aop.ApiOperLog;
import work.metanet.aop.ApiOperLog.ACTION;
import work.metanet.api.storeApp.IStoreAppService;
import work.metanet.api.storeApp.protocol.ReqRemoveStoreApp;
import work.metanet.api.storeApp.protocol.ReqSaveStoreApp;
import work.metanet.api.storeApp.protocol.ReqStoreAppInfo;
import work.metanet.api.storeApp.protocol.ReqStoreAppInfo.RespStoreAppInfo;
import work.metanet.api.storeApp.protocol.ReqStoreAppList;
import work.metanet.api.storeApp.protocol.ReqStoreAppList.RespStoreAppList;
import work.metanet.base.BaseController;
import work.metanet.base.RespPaging;
import work.metanet.base.Result;
import work.metanet.base.ResultMessage;

@ApiModule(module = Module.STORE_APP)
@Controller
@RequestMapping("storeApp")
public class StoreAppController extends BaseController{
	
	@DubboReference IStoreAppService storeAppService;
	
	@RequestMapping("listPage")
	public String listPage() {
		return "page/storeApp/list";
	}
	
	@RequestMapping("editPage")
	public String editPage(HttpServletRequest request,String storeAppId) throws Exception {
		RespStoreAppInfo info = new RespStoreAppInfo();
		if(StringUtils.isNotBlank(storeAppId)) {
			info = storeAppService.storeAppInfo(new ReqStoreAppInfo().setStoreAppId(storeAppId));
		}
		request.setAttribute("info", info);
		return "page/storeApp/edit";
	}
	
	@ApiOperLog(action = ACTION.INFO)
	@RequestMapping("infoPage")
	public String infoPage(HttpServletRequest request,String storeAppId) throws Exception {
		RespStoreAppInfo info = new RespStoreAppInfo();
		if(StringUtils.isNotBlank(storeAppId)) {
			info = storeAppService.storeAppInfo(new ReqStoreAppInfo().setStoreAppId(storeAppId));
		}
		request.setAttribute("info", info);
		return "page/storeApp/info";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("storeAppList")
	public Result<RespPaging<RespStoreAppList>> deviceList(@RequestBody ReqStoreAppList req) throws Exception {
		req.setChannelId(getChannelId());
		return ResultMessage.SUCCESS.result(storeAppService.storeAppList(req));
	}
	
	@ApiOperLog(action = ACTION.SAVE)
	@ResponseBody
	@RequestMapping("saveStoreApp")
	public Result<?> saveStoreApp(@RequestBody ReqSaveStoreApp req) throws Exception {
		req.setChannelId(getChannelId());
		storeAppService.saveStoreApp(req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperLog(action = ACTION.DELETE)
	@ResponseBody
	@RequestMapping("removeStoreApp")
	public Result<?> removeStoreApp(@Valid @RequestBody List<ReqRemoveStoreApp> req) throws Exception {
		storeAppService.removeStoreApp(req);
		return ResultMessage.SUCCESS.result();
	}
	
	
}
