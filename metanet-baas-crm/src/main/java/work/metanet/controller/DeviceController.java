package work.metanet.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import work.metanet.aop.ApiModule;
import work.metanet.aop.ApiModule.Module;
import work.metanet.aop.ApiOperLog;
import work.metanet.aop.ApiOperLog.ACTION;
import work.metanet.api.device.IDeviceService;
import work.metanet.api.device.protocol.ReqDeviceInfo;
import work.metanet.api.device.protocol.ReqDeviceInfo.RespDeviceInfo;
import work.metanet.api.device.protocol.ReqDeviceList;
import work.metanet.api.device.protocol.ReqDeviceList.RespDeviceList;
import work.metanet.api.device.protocol.ReqImportDevice;
import work.metanet.api.device.protocol.ReqRemoveDevice;
import work.metanet.api.device.protocol.ReqSaveDevice;
import work.metanet.base.BaseController;
import work.metanet.base.RespPaging;
import work.metanet.base.Result;
import work.metanet.base.ResultMessage;
import work.metanet.exception.LxException;
import work.metanet.vo.ImportMsgVo;
import work.metanet.vo.ImportMsgVo.ImportFailVo;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

@ApiModule(module = Module.DEVICE)
@Controller
@RequestMapping("device")
public class DeviceController extends BaseController{
	
	@DubboReference IDeviceService deviceService;
	
	@RequestMapping("listPage")
	public String listPage() {
		return "page/device/list";
	}
	
	@RequestMapping("editPage")
	public String editPage(HttpServletRequest request,String deviceId) throws Exception {
		RespDeviceInfo info = new RespDeviceInfo();
		if(StringUtils.isNotBlank(deviceId)) {
			info = deviceService.deviceInfo(null,new ReqDeviceInfo().setDeviceId(deviceId));
		}
		request.setAttribute("info", info);
		return "page/device/edit";
	}
	
	@ApiOperLog(action = ACTION.INFO)
	@RequestMapping("infoPage")
	public String infoPage(HttpServletRequest request,String deviceId) throws Exception {
		RespDeviceInfo info = new RespDeviceInfo();
		if(StringUtils.isNotBlank(deviceId)) {
			info = deviceService.deviceInfo(null,new ReqDeviceInfo().setDeviceId(deviceId));
		}
		request.setAttribute("info", info);
		return "page/device/info";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("deviceList")
	public Result<RespPaging<RespDeviceList>> deviceList(@RequestBody ReqDeviceList req) throws Exception {
		return ResultMessage.SUCCESS.result(deviceService.deviceList(req));
	}
	
	@ResponseBody
	@RequestMapping("deviceInfo")
	public Result<RespDeviceInfo> deviceInfo(@RequestBody ReqDeviceInfo req) throws Exception {
		return ResultMessage.SUCCESS.result(deviceService.deviceInfo(null,req));
	}
	
	@ApiOperLog(action = ACTION.SAVE)
	@ResponseBody
	@RequestMapping("saveDevice")
	public Result<?> saveDevice(@RequestBody ReqSaveDevice req) throws Exception {
		deviceService.saveDevice(req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperLog(action = ACTION.DELETE)
	@ResponseBody
	@RequestMapping("removeDevice")
	public Result<?> removeDevice(@Valid @RequestBody List<ReqRemoveDevice> req) throws Exception {
		deviceService.removeDevice(req);
		return ResultMessage.SUCCESS.result();
	}
	
	/**
	@ApiOperLog(action = ACTION.UPDATE, desc = "启用/禁用")
	@ResponseBody
	@RequestMapping("enableDevice")
	public Result<?> enableDevice(@RequestBody ReqEnableDevice req) throws Exception {
		deviceService.enableDevice(req);
		return ResultMessage.SUCCESS.result();
	}*/
	
	/**
	@RequestMapping("downloadTemplate")
	public void  download(HttpServletResponse response, HttpServletRequest request) throws Exception{
		ExcelWriter writer = ExcelUtil.getWriter("classpath:/static/excel/设备.xlsx");
		//response为HttpServletResponse对象
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		//test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
		response.setHeader("Content-Disposition","attachment;filename=device.xlsx"); 
		ServletOutputStream out = response.getOutputStream(); 

		writer.flush(out, true);
		// 关闭writer，释放内存
		writer.close();
		//此处记得关闭输出Servlet流
		IoUtil.close(out);
	}*/
	
	@ApiOperLog(action = ACTION.IMPORT)
	@ResponseBody
	@RequestMapping(value = "importDevice", headers = "content-type=multipart/form-data")
	public Result<?> importDevice(HttpServletResponse response, @RequestParam("file") MultipartFile file) throws Exception {
		ExcelReader reader = ExcelUtil.getReader(file.getInputStream(),0);
		List<ReqImportDevice> list = reader.read(1,2,ReqImportDevice.class);
		reader.close();
		
		if(CollectionUtil.isEmpty(list)) throw LxException.of().setMsg("导入数据不能为空");
		
		ImportMsgVo importMsgVo = new ImportMsgVo();
		List<ImportFailVo> importErrorVos = new ArrayList<ImportFailVo>();
		importMsgVo.setTotalCount(list.size());
		
		Integer row = 3;
		for (ReqImportDevice device : list) {
			
			boolean flag = true;
			
			if(StrUtil.isBlank(device.getSerialNumber()) && StrUtil.isBlank(device.getWirelessMac())) {
				importErrorVos.add(new ImportFailVo().setRow(row).setMsg("序列号与MAC不能同时为空"));
				flag = false;
			}
			
			if(StrUtil.isBlank(device.getModelName())) {
				importErrorVos.add(new ImportFailVo().setRow(row).setMsg("型号不能为空"));
				flag = false;
			}
			
			if(StrUtil.isBlank(device.getBrandName())) {
				importErrorVos.add(new ImportFailVo().setRow(row).setMsg("品牌不能为空"));
				flag = false;
			}
			
			if(StrUtil.isBlank(device.getPackageName())) {
				importErrorVos.add(new ImportFailVo().setRow(row).setMsg("产品包名不能为空"));
				flag = false;
			}
			
			if(!flag) {
				importMsgVo.setFailCount(importMsgVo.getFailCount()+1);
				row++;
				continue;
			}
			
			try {
				deviceService.importDevice(device);
				importMsgVo.setSuccessCount(importMsgVo.getSuccessCount()+1);
			} catch (LxException e) {
				importErrorVos.add(new ImportFailVo().setRow(row).setMsg(e.getResult().getMsg()));
				importMsgVo.setFailCount(importMsgVo.getFailCount()+1);
			}
			row++;
		}
		importMsgVo.setFails(importErrorVos);
		return ResultMessage.SUCCESS.result(importMsgVo);
	}
	
	
}
