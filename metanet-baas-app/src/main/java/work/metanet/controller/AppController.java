package work.metanet.controller;

import javax.validation.Valid;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import work.metanet.api.app.IAppService;
import work.metanet.api.device.IDeviceService;
import work.metanet.api.upgradePlan.protocol.ReqUpgrade;
import work.metanet.api.upgradePlan.protocol.ReqUpgrade.RespUpgrade;
import work.metanet.api.versionModule.IAppVersionModuleService;
import work.metanet.api.versionModule.protocol.ReqViewAppVersionModule.RespViewAppVersionModule;
import work.metanet.base.BaseController;
import work.metanet.base.Result;
import work.metanet.base.ResultMessage;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags={"产品"})
@RestController
@RequestMapping("api/app")
public class AppController extends BaseController{
	
	@DubboReference
	private IAppService appService;
	@DubboReference
	private IDeviceService deviceService;
	@DubboReference
	private IAppVersionModuleService appVersionModuleService;
	
	@ApiOperation(value="产品升级")
	@PostMapping("upgrade")
	public Result<RespUpgrade> upgrade(@Valid @RequestBody ReqUpgrade req) throws Exception {
		//完善旧数据缺失导致认证失败问题
		if(StrUtil.isNotBlank(req.getWirelessMac()) && StrUtil.isNotBlank(req.getSerialNumber())) {
			deviceService.repairDeviceSerialNumber(req.getWirelessMac(), req.getSerialNumber());
		}
		if(StrUtil.isNotBlank(req.getDeviceId())) {
			return ResultMessage.SUCCESS.result(appService.upgrade(req, getVersionCode(), getPackageName()));			
		}else {
			return ResultMessage.SUCCESS.result(appService.upgrade_v2(req, getVersionCode(), getPackageName()));
		}
	}
	
	@ApiOperation(value="展示产品模块")
	@PostMapping("viewAppVersionModule")
	public Result<RespViewAppVersionModule> viewAppVersionModule() throws Exception {
		return ResultMessage.SUCCESS.result(appVersionModuleService.viewAppVersionModule(getPackageName(),getVersionCode()));
	}
	
}
