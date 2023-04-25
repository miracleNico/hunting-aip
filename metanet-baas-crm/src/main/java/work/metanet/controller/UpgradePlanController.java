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
import work.metanet.api.upgradePlan.IUpgradePlanService;
import work.metanet.api.upgradePlan.protocol.ReqRemoveUpgradePlan;
import work.metanet.api.upgradePlan.protocol.ReqSaveUpgradePlan;
import work.metanet.api.upgradePlan.protocol.ReqSendUpgradePlan;
import work.metanet.api.upgradePlan.protocol.ReqUpgradePlanInfo;
import work.metanet.api.upgradePlan.protocol.ReqUpgradePlanInfo.RespUpgradePlanInfo;
import work.metanet.api.upgradePlan.protocol.ReqUpgradePlanList;
import work.metanet.api.upgradePlan.protocol.ReqUpgradePlanList.RespUpgradePlanList;
import work.metanet.base.BaseController;
import work.metanet.base.RespPaging;
import work.metanet.base.Result;
import work.metanet.base.ResultMessage;

@ApiModule(module = Module.UPGRADE_PLAN)
@Controller
@RequestMapping("upgradePlan")
public class UpgradePlanController extends BaseController{
	
	@DubboReference IUpgradePlanService upgradePlanService;
	
	@RequestMapping("listPage")
	public String listPage() {
		return "page/upgradePlan/list";
	}
	
	@RequestMapping("editPage")
	public String editPage(HttpServletRequest request,String upgradePlanId) throws Exception {
		RespUpgradePlanInfo info = new RespUpgradePlanInfo();
		if(StringUtils.isNotBlank(upgradePlanId)) {
			info = upgradePlanService.upgradePlanInfo(new ReqUpgradePlanInfo().setUpgradePlanId(upgradePlanId));
		}
		request.setAttribute("info", info);
		return "page/upgradePlan/edit";
	}
	
	@ApiOperLog(action = ACTION.INFO)
	@RequestMapping("infoPage")
	public String infoPage(HttpServletRequest request,String upgradePlanId) throws Exception {
		RespUpgradePlanInfo info = new RespUpgradePlanInfo();
		if(StringUtils.isNotBlank(upgradePlanId)) {
			info = upgradePlanService.upgradePlanInfo(new ReqUpgradePlanInfo().setUpgradePlanId(upgradePlanId));
		}
		request.setAttribute("info", info);
		return "page/upgradePlan/info";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("upgradePlanList")
	public Result<RespPaging<RespUpgradePlanList>> deviceList(@RequestBody ReqUpgradePlanList req) throws Exception {
		return ResultMessage.SUCCESS.result(upgradePlanService.upgradePlanList(req));
	}
	
	@ApiOperLog(action = ACTION.SAVE)
	@ResponseBody
	@RequestMapping("saveUpgradePlan")
	public Result<?> saveUpgradePlan(@RequestBody ReqSaveUpgradePlan req) throws Exception {
		upgradePlanService.saveUpgradePlan(req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperLog(action = ACTION.DELETE)
	@ResponseBody
	@RequestMapping("removeUpgradePlan")
	public Result<?> removeUpgradePlan(@Valid @RequestBody List<ReqRemoveUpgradePlan> req) throws Exception {
		upgradePlanService.removeUpgradePlan(req);
		return ResultMessage.SUCCESS.result();
	}
	
	/**
	 * @Description: 发布或取消发布升级计划
	 * @Author wanbo
	 * @DateTime 2019/11/29
	 */
	@ApiOperLog(action = ACTION.UPDATE, desc = "发布/取消发布")
	@ResponseBody
	@RequestMapping("sendUpgradePlan")
	public Result<?> sendUpgradePlan(@Valid @RequestBody ReqSendUpgradePlan req) throws Exception {
		upgradePlanService.sendUpgradePlan(req);
		return ResultMessage.SUCCESS.result();
	}
	
	
}
