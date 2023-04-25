package work.metanet.server.service;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import work.metanet.api.device.IDeviceService;
import work.metanet.api.device.protocol.ReqActivate;
import work.metanet.api.device.protocol.ReqActivate.RespActivate;
import work.metanet.api.device.protocol.ReqDeviceAuth;
import work.metanet.api.device.protocol.ReqDeviceAuth.RespDeviceAuth;
import work.metanet.api.device.protocol.ReqDeviceInfo;
import work.metanet.api.device.protocol.ReqDeviceInfo.RespDeviceInfo;
import work.metanet.api.device.protocol.ReqDeviceList;
import work.metanet.api.device.protocol.ReqDeviceList.RespDeviceList;
import work.metanet.api.device.protocol.ReqEnableDevice;
import work.metanet.api.device.protocol.ReqImportDevice;
import work.metanet.api.device.protocol.ReqRemoveDevice;
import work.metanet.api.device.protocol.ReqSaveDevice;
import work.metanet.api.device.vo.DeviceAppVo;
import work.metanet.api.statistical.vo.ChartVo;
import work.metanet.base.RespPaging;
import work.metanet.base.ResultMessage;
import work.metanet.constant.ConstSource;
import work.metanet.exception.DaasException;
import work.metanet.model.App;
import work.metanet.model.Brand;
import work.metanet.model.Device;
import work.metanet.model.DeviceApp;
import work.metanet.model.Model;
import work.metanet.server.dao.AppMapper;
import work.metanet.server.dao.BrandMapper;
import work.metanet.server.dao.DeviceAppMapper;
import work.metanet.server.dao.DeviceMapper;
import work.metanet.server.dao.ModelMapper;
import work.metanet.server.dao.SequenceMapper;
import work.metanet.server.dao.SerialNumberMapper;
import work.metanet.server.vo.AuthDeviceParam;
import work.metanet.server.vo.DeviceVo;
import work.metanet.server.vo.SerialNumberVo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;

@DubboService
public class DeviceService implements IDeviceService{
	
	@Autowired
	private DeviceMapper deviceMapper;
	@Autowired
	private SerialNumberMapper serialNumberMapper;
	@Autowired
	private AppMapper appMapper;
	@Autowired
	private BrandMapper brandMapper;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private SequenceMapper sequenceMapper;
	@Autowired
	private DeviceAppMapper deviceAppMapper;
	
	@Override
	public List<ChartVo> appDeviceActiveChart(String channelId) throws Exception {
		return deviceMapper.appDeviceActiveChart(channelId);
	}
	
	@Override
	public List<ChartVo> appDeviceChart(String channelId) throws Exception {
		return deviceMapper.appDeviceChart(channelId);
	}
	
	@Override
	public Integer deviceActiveTotal(String channelId) throws Exception {
		return deviceMapper.deviceActiveTotal(channelId);
	}
	
	@Override
	public Integer deviceTotal(String channelId) throws Exception {
		return deviceMapper.deviceTotal(channelId);
	}
	
	@Async
	@Override
	public void repairDeviceSerialNumber(String wirelessMac, String serialNumber) {
		deviceMapper.repairDeviceSerialNumber(wirelessMac, serialNumber);
	}
	
	private RespDeviceAuth activeDeviceApp(String deviceId,String appId) {
		//查找设备产品
		DeviceApp deviceApp = deviceAppMapper.getDeviceApp(MapUtil.builder().put("deviceId", deviceId).put("appId", appId).build());
		if(BeanUtil.isNotEmpty(deviceApp)) {
			if(BeanUtil.isEmpty(deviceApp.getActivateTime())) {
				deviceApp.setActivateTime(new Date());
				deviceAppMapper.updateByPrimaryKeySelective(deviceApp);
			}
			return new RespDeviceAuth().setDeviceId(deviceId).setCid(deviceApp.getDeviceAppId());				
		}
		return null;
	}
	
	/**
	 * @Description: 设备认证
	 * @Author wanbo
	 * @DateTime 2019/11/18
	 */
	@Override
	public RespDeviceAuth deviceAuth(ReqDeviceAuth req) throws Exception {
		if(StringUtils.isBlank(req.getSerialNumber()) && StringUtils.isBlank(req.getMac())) throw DaasException.of().setMsg("序列号与MAC不能同时为空");
		
		App app = appMapper.getApp(MapUtil.builder("packageName",req.getPackageName()).build());
		if(BeanUtil.isEmpty(app)) throw DaasException.of().setMsg("产品信息错误");
		
		AuthDeviceParam authDeviceParam = AuthDeviceParam.of()
				.setBrandName(req.getBrandName())
				.setModelName(req.getModelName())
				.setDeviceAuthType(app.getAuthType())
				.setMac(req.getMac())
				.setSerialNumber(req.getSerialNumber())
				;
		
		//获取认证设备
		DeviceVo deviceVo = deviceMapper.getAuthDevice(authDeviceParam);
		
		if(BeanUtil.isNotEmpty(deviceVo)) {
			//设备信息补全
			if(StrUtil.isNotBlank(req.getMac()) && StrUtil.isNotBlank(req.getSerialNumber())) {
				deviceMapper.repairDevice(deviceVo.getDeviceId(), req.getMac(), req.getSerialNumber());
			}
			RespDeviceAuth respDeviceAuth = activeDeviceApp(deviceVo.getDeviceId(), app.getAppId());
			if(BeanUtil.isNotEmpty(respDeviceAuth)) {
				return respDeviceAuth;
			}
		}
		
		//启用激活码方式
		if(app.getEnableSn()) {
			if(BeanUtil.isEmpty(deviceVo)) throw DaasException.of().setResult(ResultMessage.FAILURE_DEVICE_NOT_ACTIVATE.result());
			if (!deviceVo.getEnableStatus()) throw DaasException.of().setResult(ResultMessage.FAILURE_DEVICE_DISABLE.result());
			
			RespDeviceAuth respDeviceAuth = activeDeviceApp(deviceVo.getDeviceId(), app.getAppId());
			if(BeanUtil.isNotEmpty(respDeviceAuth)) {
				return respDeviceAuth;
			}else {
				throw DaasException.of().setResult(ResultMessage.FAILURE_DEVICE_NOT_ACTIVATE.result());
			}
		}else {//禁用激活码方式
			SerialNumberVo serialNumber = serialNumberMapper.getSerialNumberOnlyOne(app.getAppId());
			if(BeanUtil.isEmpty(serialNumber)) throw DaasException.of().setMsg("未找到激活码，请联系管理员！");
			ReqActivate reqActivate = new ReqActivate();
			BeanUtil.copyProperties(req, reqActivate);
			reqActivate.setSnCode(serialNumber.getSnCode());
			deviceVo = activate(reqActivate, app);
			return new RespDeviceAuth().setDeviceId(deviceVo.getDeviceId()).setCid(deviceVo.getDeviceAppId());
		}
	}
	
	private DeviceVo activate(ReqActivate req,App app) throws Exception{
		//验证激活码
		SerialNumberVo serialNumber = validationSn(req.getSnCode(),app);
		//检查品牌
		Brand brand = checkBrand(req.getBrandName(),ConstSource.ENTRY);
		//检查型号
		Model model = checkBrandModel(req.getModelName(), brand.getBrandId(),ConstSource.ENTRY);
		
		AuthDeviceParam authDeviceParam = AuthDeviceParam.of()
				.setBrandName(req.getBrandName())
				.setModelName(req.getModelName())
				.setDeviceAuthType(app.getAuthType())
				.setMac(req.getMac())
				.setSerialNumber(req.getSerialNumber())
				;
		//获取认证设备
		DeviceVo deviceVo = deviceMapper.getAuthDevice(authDeviceParam);
		if(BeanUtil.isEmpty(deviceVo)) {
			//没有设备记录则新增
			deviceVo = new DeviceVo();
 			BeanUtil.copyProperties(req, deviceVo);
 			deviceVo.setDeviceId(sequenceMapper.generateDeviceId())
					.setWirelessMac(req.getMac())
					.setSerialNumber(req.getSerialNumber())
					.setUuid(req.getUuid())
					.setBrandId(brand.getBrandId())
					.setModelId(model.getModelId())
 					.setSource(ConstSource.ENTRY.getVal())
 					.setEnableStatus(true)
 					;
 			deviceVo.setDeviceName(brand.getBrandName()+"-"+model.getModelName()+"-"+deviceVo.getDeviceId());
 			//新增设备
			deviceMapper.insertSelective(deviceVo);
		}
		
		//验证设备禁用状态
		if (!deviceVo.getEnableStatus()) throw DaasException.of().setResult(ResultMessage.FAILURE_DEVICE_DISABLE.result());
		
		//查找设备产品
		DeviceApp deviceApp = deviceAppMapper.getDeviceApp(MapUtil.builder().put("deviceId", deviceVo.getDeviceId()).put("appId", app.getAppId()).build());
		if(BeanUtil.isEmpty(deviceApp)) {
			//新增设备产品激活信息
			deviceApp = new DeviceApp()
					.setDeviceAppId(IdUtil.fastSimpleUUID())
					.setDeviceId(deviceVo.getDeviceId())
					.setAppId(app.getAppId())
					.setSnCode(serialNumber.getSnCode())
					.setActivateTime(new Date());
			deviceAppMapper.insertSelective(deviceApp);
		}else {
			//更新设备产品激活信息
			deviceApp.setSnCode(serialNumber.getSnCode());
			deviceApp.setActivateTime(new Date());
			deviceAppMapper.updateByPrimaryKeySelective(deviceApp);
		}
		
		//更新激活码使用信息
		serialNumberMapper.updateSerialNumberUseNumber(serialNumber.getSnCode());
		
		return deviceVo.setDeviceAppId(deviceApp.getDeviceAppId());
	}
	
	/**
	 * @Description: 设备激活
	 * @Author wanbo
	 * @DateTime 2019/11/18
	 */
	@Override
	@Transactional
	public RespActivate activate(ReqActivate req) throws Exception{
		if(StringUtils.isBlank(req.getSerialNumber()) && StringUtils.isBlank(req.getMac())) throw DaasException.of().setMsg("序列号与MAC不能同时为空");
		
		App app = appMapper.getApp(MapUtil.builder("packageName",req.getPackageName()).build());
		if(BeanUtil.isEmpty(app)) throw DaasException.of().setMsg("产品信息错误");
		
		DeviceVo deviceApp = activate(req, app);
		return new RespActivate().setDeviceId(deviceApp.getDeviceId()).setCid(deviceApp.getDeviceAppId());
	}
	
	/**
	 * @Description: 检查品牌
	 * @Author wanbo
	 * @DateTime 2019/11/16
	 */
	private Brand checkBrand(String brandName,ConstSource constSource) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("brandName", brandName);
		Brand brand = brandMapper.getBrand(map);
		if(BeanUtil.isEmpty(brand)) {
			brand = new Brand()
					.setBrandId(sequenceMapper.generateBrandId())
					.setSource(constSource.getVal())
					.setBrandName(brandName);
			brandMapper.insertSelective(brand);
		}
		return brand;
	}
	
	/**
	 * @Description: 检查型号
	 * @Author wanbo
	 * @DateTime 2019/11/16
	 */
	private Model checkBrandModel(String modelName,String brandId,ConstSource constSource) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("modelName", modelName);
		map.put("brandId", brandId);
		Model model = modelMapper.getModel(map);
		if(BeanUtil.isEmpty(model)) {
			model = new Model()
					.setModelId(sequenceMapper.generateModelId())
					.setBrandId(brandId)
					.setSource(constSource.getVal())
					.setModelName(modelName)
					.setBrandId(brandId);
			modelMapper.insertSelective(model);
		}
		return model;
	}
	
	
	/**
	 * @Description: 验证激活码
	 * @Author wanbo
	 * @DateTime 2020/03/09
	 */
	private SerialNumberVo validationSn(String snCode,App app) throws Exception{
		if(StringUtils.isBlank(snCode))  throw DaasException.of().setMsg("激活码不能为空");
		SerialNumberVo serialNumber = serialNumberMapper.getSerialNumber(snCode);
		if(BeanUtil.isEmpty(serialNumber))  throw DaasException.of().setMsg("激活码无效");
		if(serialNumber.getUseNumber()>=serialNumber.getMaxUseNumber()) throw DaasException.of().setMsg("激活码使用次数已达上限");
		if(!serialNumber.getAppId().equals(app.getAppId())) throw DaasException.of().setMsg("激活码不能在用于此产品");
		return serialNumber;
	}
	
	/**
	 * @Description: 设备列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespPaging<RespDeviceList> deviceList(ReqDeviceList req) throws Exception {
		RespPaging<RespDeviceList> respPaging = new RespPaging<RespDeviceList>();
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<RespDeviceList> list = deviceMapper.deviceList(BeanUtil.beanToMap(req));
		BeanUtil.copyProperties(new PageInfo<RespDeviceList>(list),respPaging);
		for (RespDeviceList resp : respPaging.getList()) {
			resp.setSource(ConstSource.getText(resp.getSource()));
		}
		return respPaging;
	}
	
	
	/**
	 * @Description: 设备详情
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespDeviceInfo deviceInfo(String channelId,ReqDeviceInfo req) throws Exception {
		RespDeviceInfo resp = deviceMapper.deviceInfo(BeanUtil.beanToMap(req));
		if(req!=null) {
			resp.setSource(ConstSource.getText(resp.getSource()));
			resp.setDeviceApps(deviceAppMapper.deviceAppDeailList(channelId, resp.getDeviceId()));
		}
		return resp;
	}
	
	
	@Override
	@Transactional
	public void importDevice(ReqImportDevice req) throws Exception {
		App app = appMapper.getApp(MapUtil.builder("packageName",req.getPackageName()).build());
		if(BeanUtil.isEmpty(app)) throw DaasException.of().setMsg("产品信息错误");
		
		//检查品牌
		Brand brand = checkBrand(req.getBrandName(),ConstSource.IMPORT);
		
		//检查型号
		Model model = checkBrandModel(req.getModelName(), brand.getBrandId(),ConstSource.IMPORT);
		
		AuthDeviceParam authDeviceParam = AuthDeviceParam.of()
				.setBrandName(req.getBrandName())
				.setModelName(req.getModelName())
				.setDeviceAuthType(app.getAuthType())
				.setMac(req.getWirelessMac())
				.setSerialNumber(req.getSerialNumber())
				;
		//获取认证设备信息
		Device device = deviceMapper.getAuthDevice(authDeviceParam);
		
		//没有设备记录则新增
		if(BeanUtil.isEmpty(device)) {
			device = new Device()
					.setDeviceId(sequenceMapper.generateDeviceId())
					.setBrandId(brand.getBrandId())
					.setModelId(model.getModelId())
					.setSource(ConstSource.IMPORT.getVal())
					;
			device.setDeviceName(brand.getBrandName()+"-"+model.getModelName()+"-"+device.getDeviceId());
			BeanUtil.copyProperties(req, device, CopyOptions.create().ignoreNullValue());
			//新增设备
			deviceMapper.insertSelective(device);
		}
		
		//查找设备产品
		DeviceApp deviceApp = deviceAppMapper.getDeviceApp(MapUtil.builder().put("deviceId", device.getDeviceId()).put("appId", app.getAppId()).build());
		
		if(BeanUtil.isNotEmpty(deviceApp)) throw DaasException.of().setMsg("设备产品已存在");
		
		//新增设备产品
		deviceApp = new DeviceApp().setDeviceAppId(IdUtil.fastSimpleUUID()).setDeviceId(device.getDeviceId()).setAppId(app.getAppId());
		deviceAppMapper.insertSelective(deviceApp);
	}
	
	
	/**
	 * @Description:保存设备
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Transactional
	@Override
	public void saveDevice(ReqSaveDevice req) throws Exception {
		if(CollectionUtil.isEmpty(req.getDeviceApps())) throw DaasException.of().setMsg("产品不能为空");
		
		App app = appMapper.selectByPrimaryKey(req.getDeviceApps().get(0).getAppId());
		
		Device device = new Device();
		BeanUtil.copyProperties(req, device);
		
		Brand brand = brandMapper.selectByPrimaryKey(req.getBrandId());
		
		Model model = modelMapper.selectByPrimaryKey(req.getModelId());
		
		AuthDeviceParam authDeviceParam = AuthDeviceParam.of()
				.setDeviceId(req.getDeviceId())
				.setBrandName(brand.getBrandName())
				.setModelName(model.getModelName())
				.setDeviceAuthType(app.getAuthType())
				.setMac(req.getWirelessMac())
				.setSerialNumber(req.getSerialNumber())
				;
		
		//获取认证设备
		Device dbDevice = deviceMapper.getAuthDevice(authDeviceParam);
		
		if(StringUtils.isNotBlank(device.getDeviceId())) {
			//修改操作
			if(BeanUtil.isNotEmpty(dbDevice) && !dbDevice.getDeviceId().equals(device.getDeviceId())) throw DaasException.of().setMsg("设备已存在");
			if(BeanUtil.isEmpty(dbDevice) || dbDevice.getDeviceId().equals(device.getDeviceId())) {
				if(StringUtils.isBlank(device.getDeviceName())) {
					device.setDeviceName(brand.getBrandName()+"-"+model.getModelName()+"-"+device.getDeviceId());
				}
				deviceMapper.updateByPrimaryKeySelective(device);
			}else {
				throw DaasException.of().setResult(ResultMessage.FAILURE.result());
			}
		}else {
			//新增操作
			if(BeanUtil.isNotEmpty(dbDevice))throw DaasException.of().setMsg("设备已存在");
			
			device.setDeviceId(sequenceMapper.generateDeviceId());
			if(StringUtils.isBlank(device.getDeviceName())) {
				device.setDeviceName(brand.getBrandName()+"-"+model.getModelName()+"-"+device.getDeviceId());
			}
			deviceMapper.insertSelective(device);
		}
		
		/*************************设置设备产品******************************/
		{
			List<DeviceApp> dbList = deviceAppMapper.deviceAppList(device.getDeviceId());
			List<DeviceAppVo> reqList = req.getDeviceApps();
			Map<String, DeviceApp> dbMap = dbList.stream().collect(Collectors.toMap(DeviceApp::getAppId,Function.identity()));
			Map<String, DeviceAppVo> reqMap = req.getDeviceApps().stream().collect(Collectors.toMap(DeviceAppVo::getAppId,Function.identity()));
			
			dbList.forEach(dbDeviceApp->{
				DeviceAppVo reqDeviceApp = reqMap.get(dbDeviceApp.getAppId());
				//删除
				if(BeanUtil.isEmpty(reqDeviceApp) && BeanUtil.isEmpty(dbDeviceApp.getActivateTime())) {
					deviceAppMapper.deleteByPrimaryKey(dbDeviceApp.getDeviceAppId());
				}
			});
			
			reqList.forEach(reqDeviceApp->{
				DeviceApp dbDeviceApp = dbMap.get(reqDeviceApp.getAppId());
				if(BeanUtil.isEmpty(dbDeviceApp)) {//新增
					dbDeviceApp = new DeviceApp();
					BeanUtil.copyProperties(reqDeviceApp, dbDeviceApp);
					dbDeviceApp.setDeviceAppId(IdUtil.fastSimpleUUID());
					dbDeviceApp.setDeviceId(device.getDeviceId());
					deviceAppMapper.insertSelective(dbDeviceApp);
				}else {//修改
					BeanUtil.copyProperties(reqDeviceApp, dbDeviceApp,CopyOptions.create().ignoreNullValue());
					deviceAppMapper.updateByPrimaryKeySelective(dbDeviceApp);				
				}
			});
		}
	}
	
	
	/**
	 * @Description: 删除设备
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public void removeDevice(List<ReqRemoveDevice> devices) throws Exception {
		deviceMapper.removeDevice(devices);
	}
	
	
	/**
	 * @Description: 启用/禁用设备
	 * @Author wanbo
	 * @DateTime 2019/11/23
	 */
	@Override
	public void enableDevice(ReqEnableDevice req) throws Exception {
		Device device = deviceMapper.selectByPrimaryKey(req.getDeviceId());
		if(BeanUtil.isEmpty(device))
			throw DaasException.of().setResult(ResultMessage.FAILURE.result());
		deviceMapper.enableDevice(req.getDeviceId(), req.getEnableStatus());
	}
	
	
	
	
}
