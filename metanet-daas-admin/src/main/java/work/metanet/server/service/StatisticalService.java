package work.metanet.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import work.metanet.api.app.IAppService;
import work.metanet.api.app.vo.AppVo;
import work.metanet.api.device.IDeviceService;
import work.metanet.api.statistical.IStatisticalService;
import work.metanet.api.statistical.protocol.ReqDayActiveUser.RespDayActiveUser;
import work.metanet.api.statistical.protocol.ReqStatistical.RespStatistical;
import work.metanet.api.statistical.vo.ActiveUserAppVo;
import work.metanet.api.user.IUserService;
import work.metanet.server.dao.StatisticalAppActiveMapper;
import work.metanet.server.vo.StatisticalVo;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

@DubboService
public class StatisticalService implements IStatisticalService{
	
	@DubboReference
	private IAppService appService;
	@Autowired
	private StatisticalAppActiveMapper statisticalAppActiveMapper;
	@DubboReference
	private IDeviceService deviceService;
	@DubboReference
	private IUserService userService;
	
	@Override
	public RespStatistical statistical(String channelId) throws Exception {
		return new RespStatistical()
				.setDeviceTotal(deviceService.deviceTotal(channelId))
				.setDeviceActiveTotal(deviceService.deviceActiveTotal(channelId))
				.setUserTotal(userService.userTotal(channelId))
				.setYesterdayActiveUserTotal(statisticalAppActiveMapper.yesterdayActiveUserTotal(channelId))
				;
	}
	
	@Override
	public RespDayActiveUser dayActiveUser(String channelId) throws Exception {
		RespDayActiveUser resp = new RespDayActiveUser();
		
		//最近7天的日期
		List<DateTime> dateList = DateUtil.rangeToList(DateUtil.offsetDay(DateUtil.date(), -6), DateUtil.date(), DateField.DAY_OF_YEAR);
		for (DateTime dateTime : dateList) {
			resp.getDates().add(dateTime.toDateStr());
		}
		
		//产品列表
		List<AppVo> appVos = appService.appVoList(channelId);
		
		//各个产品最近7天每天的日活用户数
		List<StatisticalVo> statisticalVos = statisticalAppActiveMapper.appActiveUserNumber(channelId);
		Map<String, StatisticalVo> statisticalMap = statisticalVos.stream().collect(Collectors.toMap(StatisticalVo::getId,Function.identity()));
		
		List<ActiveUserAppVo> activeUserAppVos = new ArrayList<ActiveUserAppVo>();
		for (AppVo appVo : appVos) {
			ActiveUserAppVo activeUserAppVo = new ActiveUserAppVo();
			activeUserAppVo.setName(appVo.getAppName());
			Boolean active = false;
			for (String date : resp.getDates()) {
				StatisticalVo statisticalVo = statisticalMap.get(StrUtil.join(" ", date,appVo.getAppId()));
				if(statisticalVo!=null) {
					activeUserAppVo.getData().add(statisticalVo.getNumber());
					active = true;
				}else {
					activeUserAppVo.getData().add(0);
				}
			}
			if(active)activeUserAppVos.add(activeUserAppVo);
		}
		resp.setActiveUserApps(activeUserAppVos);
		return resp;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
