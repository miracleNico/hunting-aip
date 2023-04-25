package work.metanet.controller;

import java.util.List;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import work.metanet.aop.ApiModule;
import work.metanet.aop.ApiModule.Module;
import work.metanet.aop.ApiOperLog;
import work.metanet.aop.ApiOperLog.ACTION;
import work.metanet.aop.ApiPermission;
import work.metanet.aop.ApiPermission.AUTH;
import work.metanet.api.device.IDeviceService;
import work.metanet.api.statistical.IStatisticalService;
import work.metanet.api.statistical.protocol.ReqDayActiveUser.RespDayActiveUser;
import work.metanet.api.statistical.protocol.ReqStatistical.RespStatistical;
import work.metanet.api.statistical.vo.ChartVo;
import work.metanet.base.BaseController;
import work.metanet.base.Result;
import work.metanet.base.ResultMessage;

/**
 * @Description: 统计
 * @Author wanbo
 * @DateTime 2019/12/24
 */
@ApiModule(module = Module.STATISTICAL)
@Controller
@RequestMapping("statistical")
public class StatisticalController extends BaseController{
	
	@DubboReference 
	private IStatisticalService statisticalService;
	@DubboReference
	private IDeviceService deviceService;
	
	@ApiPermission(AUTH.OPEN)
	@RequestMapping("statisticalPage")
	public String statisticalPage() {
		return "page/statistical/statistical";
	}
	
	@ApiPermission(AUTH.OPEN)
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("dayActiveUser")
	public Result<RespDayActiveUser> dayActiveUser() throws Exception {
		return ResultMessage.SUCCESS.result(statisticalService.dayActiveUser(null));
	}
	
	@ApiPermission(AUTH.OPEN)
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("statistical")
	public Result<RespStatistical> statistical() throws Exception {
		return ResultMessage.SUCCESS.result(statisticalService.statistical(null));
	}
	
	@ApiPermission(AUTH.OPEN)
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("appDeviceChart")
	public Result<List<ChartVo>> appDeviceChart() throws Exception {
		return ResultMessage.SUCCESS.result(deviceService.appDeviceChart(null));
	}
	
	@ApiPermission(AUTH.OPEN)
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("appDeviceActiveChart")
	public Result<List<ChartVo>> appDeviceActiveChart() throws Exception {
		return ResultMessage.SUCCESS.result(deviceService.appDeviceActiveChart(null));
	}
	
	
}
