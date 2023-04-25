package work.metanet.vision.service;

import java.util.List;
import java.util.Map;
import java.util.logging.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import work.metanet.vision.dao.SequenceMapper;
import work.metanet.vision.dao.VisionTestMapper;
import work.metanet.vision.dao.EyeMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import work.metanet.api.vision.IVisionTest;
import work.metanet.api.vision.protocol.ReqRemoveVisionTest;
import work.metanet.api.vision.protocol.ReqSaveEye;
import work.metanet.api.vision.protocol.ReqSaveVisionReport;
import work.metanet.api.vision.protocol.ReqSaveVisionTest;
import work.metanet.api.vision.protocol.ReqSaveVisionTest.RespSaveVisionTest;
import work.metanet.api.vision.protocol.ReqVisionTestInfo;
import work.metanet.api.vision.protocol.ReqVisionTestInfo.RespVisionTestInfo;
import work.metanet.api.vision.protocol.ReqVisionTestList;
import work.metanet.api.vision.protocol.ReqEyeInfo;
import work.metanet.api.vision.protocol.ReqEyeInfo.RespEyeInfo;
import work.metanet.api.vision.protocol.ReqSaveEye.RespSaveEye;
import work.metanet.api.vision.protocol.ReqSaveVisionReport.RespSaveVisionReport;
import work.metanet.api.vision.protocol.ReqVisionTestList.RespVisionTestList;
import work.metanet.base.RespPaging;
import work.metanet.base.ResultMessage;
import work.metanet.exception.DaasException;
import work.metanet.model.VisionTest;
import com.tencentcloudapi.tia.v20180226.models.Log;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import io.seata.spring.annotation.GlobalTransactional;

/**
 * @Description 服务：视力测试活动
 * @author EdisonFeng
 * @DateTime 2021年4月21日
 * Copyright(c) 2021. All Rights Reserved
 */
@DubboService
//@GlobalTransactional(rollbackFor = Exception.class)
public class VisionTestService implements IVisionTest {
	private static Logger log = Logger.getLogger("VisionTestService"); 
	
	@Autowired
	private VisionTestMapper visionTestMapper;
	
	@Autowired
	private EyeMapper EyeMapper;
	
	@Override
	public RespPaging<RespVisionTestList> getVisionTestList(ReqVisionTestList req) throws Exception {
//		log.info(JSONUtil.toJsonStr(req));
		
		RespPaging<RespVisionTestList> respPaging = new RespPaging<RespVisionTestList>();
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		
		List<RespVisionTestList> list = visionTestMapper.getVisionTestList(BeanUtil.beanToMap(req));
		BeanUtil.copyProperties(new PageInfo<RespVisionTestList>(list),respPaging);
		
		return respPaging;
	}

	@Override
	public RespVisionTestInfo getVisionTestInfo(ReqVisionTestInfo req) throws Exception {
		RespVisionTestInfo resp;
		
		//获取测试活动信息
		try {
			resp = visionTestMapper.getVisionTestInfo(BeanUtil.beanToMap(req));
		}
		catch(Exception e) {
			throw DaasException.of().setMsg("查询视力测试活动异常:" + e.getMessage());
		}
		if (resp == null) {
			resp = new RespVisionTestInfo();
		}
		
		return resp; 
	}

	@Transactional
	@Override
	public RespSaveVisionTest saveVisionTest(ReqSaveVisionTest req) throws Exception {
		ReqSaveVisionTest reqSaveVisionTest = new ReqSaveVisionTest();
		RespSaveVisionTest respSaveVisionTest = new RespSaveVisionTest();
		RespVisionTestInfo respVisionTestInfo = null;
		
		String visionTestID = req.getTestId();
//		if(StringUtils.isNotBlank(visionTestID)) {
//			//获取用户的眼睛信息
//			reqSaveVisionTest.setTestId(visionTestID);
//
//			Map<String, Object> map = BeanUtil.beanToMap(reqSaveVisionTest);
//			respVisionTestInfo = visionTestMapper.getVisionTestInfo(map);
//		}
//		String newVisionTestID = null;
//		if(BeanUtil.isNotEmpty(respVisionTestInfo)) {
//			newVisionTestID = respVisionTestInfo.getTestId();
//		}
		 
		if(StringUtils.isNotBlank(visionTestID)) {
			BeanUtil.copyProperties(req, reqSaveVisionTest);
			reqSaveVisionTest.setTestId(visionTestID);
			//修改操作
			if(visionTestMapper.updateVisionTest(reqSaveVisionTest) <= 0) {
				throw DaasException.of().setMsg("修改测试活动失败");
			}
			else {
				BeanUtil.copyProperties(reqSaveVisionTest, respSaveVisionTest);
			}
		}
		else {
			BeanUtil.copyProperties(req, reqSaveVisionTest);
			String eyeId = reqSaveVisionTest.getEyeId();
			if(StringUtils.isNotBlank(eyeId)) {
				ReqEyeInfo reqEyeInfo = new ReqEyeInfo();
				reqEyeInfo.setEyeId(eyeId);
				RespEyeInfo respEyeInfo = EyeMapper.getEyeInfo(BeanUtil.beanToMap(reqEyeInfo));
				if(BeanUtil.isEmpty(respEyeInfo) || StringUtils.isBlank(respEyeInfo.getEyeId())) {
					throw DaasException.of().setMsg("眼睛ID不存在：" + eyeId);
				}
			}
			//新增操作
			reqSaveVisionTest.setTestId(IdUtil.fastSimpleUUID());
			
			if(visionTestMapper.insertVisionTest(reqSaveVisionTest) <= 0) {
				throw DaasException.of().setMsg("新增测试活动失败");
			}
			else {
				BeanUtil.copyProperties(reqSaveVisionTest, respSaveVisionTest);
			}
		}
		return respSaveVisionTest;
	}

	@Override
	public String removeVisionTest(List<ReqRemoveVisionTest> visionTests) throws Exception {
		String testId = "没有权限";
		testId = visionTestMapper.removeVisionTest(visionTests);

		return testId;
	}

	@Override
	public RespSaveVisionTest insertVisionTest(ReqSaveVisionTest req) throws Exception {
		ReqSaveVisionTest reqNew = new ReqSaveVisionTest();
		RespSaveVisionTest resp = new RespSaveVisionTest();
		try {
			//获取视力测试活动ID
			String testId = req.getTestId();
			
			BeanUtil.copyProperties(req, reqNew);
			//新增操作
			if (testId == null || testId.isEmpty()) {
				reqNew.setTestId(IdUtil.fastSimpleUUID());
			}
			if (visionTestMapper.insertVisionTest(reqNew) > 0) {
				BeanUtil.copyProperties(reqNew, resp);
			}
		}
		catch(Exception e) {
			throw DaasException.of().setMsg("新增视力测试活动异常:" + e.getMessage());
		}
		
		return resp;
	}
}
