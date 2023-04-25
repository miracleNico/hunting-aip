package work.metanet.vision.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import work.metanet.vision.dao.EyeMapper;
import work.metanet.vision.dao.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import work.metanet.api.user.protocol.ReqUserInfo;
import work.metanet.api.vision.IEyeService;
import work.metanet.api.vision.protocol.ReqEyeInfo;
import work.metanet.api.vision.protocol.ReqEyeInfo.RespEyeInfo;
import work.metanet.api.vision.protocol.ReqEyeList;
import work.metanet.api.vision.protocol.ReqEyeList.RespEyeList;
import work.metanet.api.vision.protocol.ReqRemoveEye;
import work.metanet.api.vision.protocol.ReqSaveEye;
import work.metanet.api.vision.protocol.ReqSaveEye.RespSaveEye;
import work.metanet.base.RespPaging;
import work.metanet.exception.DaasException;
import work.metanet.server.vo.UserVo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;

/**
 * @Description 服务：眼睛信息
 * @author EdisonFeng
 * @DateTime 2021年4月21日
 * Copyright(c) 2021. All Rights Reserved
 */
@DubboService
//@GlobalTransactional(rollbackFor = Exception.class)
public class EyeService implements IEyeService {
	@Autowired
	private EyeMapper eyeMapper;
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public RespPaging<RespEyeList> getEyeList(ReqEyeList req) throws Exception {
		RespPaging<RespEyeList> respPaging = new RespPaging<RespEyeList>();
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		
		List<RespEyeList> list = eyeMapper.getEyeList(BeanUtil.beanToMap(req));
		BeanUtil.copyProperties(new PageInfo<RespEyeList>(list),respPaging);
		
		return respPaging;
	}

	@Override
	public RespEyeInfo getEyeInfo(ReqEyeInfo req) throws Exception {
		//获取用户的眼睛信息
		RespEyeInfo resp = null;
		try {
			resp = eyeMapper.getEyeInfo(BeanUtil.beanToMap(req));
		}
		catch(Exception e) {
			throw DaasException.of().setMsg("获取眼睛信息异常:" + e.getMessage());
		}
		if (resp == null) {
			throw DaasException.of().setMsg("查询返回为空");
		}
		
		return resp;
	}

	@Transactional
	@Override
	public RespSaveEye saveEye(ReqSaveEye req) throws Exception {
		ReqSaveEye reqSaveEye = new ReqSaveEye();
		ReqUserInfo reqUserInfo = new ReqUserInfo();
		RespSaveEye respSaveEye = new RespSaveEye();
		RespEyeInfo respEyeInfo = null;
		UserVo respUserInfo = null;
		
		String userID = req.getUserId();
		if(StringUtils.isNotBlank(userID)) {
			//获取用户信息
			reqUserInfo.setUserId(userID);

			Map<String, Object> map = BeanUtil.beanToMap(reqUserInfo);
			respUserInfo = userMapper.getUser(map);
		}
		if(BeanUtil.isEmpty(respUserInfo) || StringUtils.isBlank(respUserInfo.getUserId())) {
			throw DaasException.of().setMsg("UserID不存在");
		}
		
		String eyeID = req.getEyeId();
		if(StringUtils.isNotBlank(eyeID)) {
			//获取用户的眼睛信息
			reqSaveEye.setEyeId(eyeID);

			Map<String, Object> map = BeanUtil.beanToMap(reqSaveEye);
			respEyeInfo = eyeMapper.getEyeInfo(map);
		}
		String newEyeID = null;
		if(BeanUtil.isNotEmpty(respEyeInfo)) {
			newEyeID = respEyeInfo.getEyeId();
		}
		 
		if(StringUtils.isNotBlank(newEyeID)) {
			BeanUtil.copyProperties(req, reqSaveEye);
			reqSaveEye.setEyeId(newEyeID);
			//修改操作
			if(eyeMapper.updateEye(reqSaveEye) <= 0) {
				throw DaasException.of().setMsg("修改失败");
			}
			else {
				BeanUtil.copyProperties(reqSaveEye, respSaveEye);
			}
		}
		else {
			BeanUtil.copyProperties(req, reqSaveEye);

			//新增操作
			reqSaveEye.setEyeId(IdUtil.fastSimpleUUID());
			
			if(eyeMapper.insertEye(reqSaveEye) <= 0) {
				throw DaasException.of().setMsg("新增失败");
			}
			else {
				BeanUtil.copyProperties(reqSaveEye, respSaveEye);
			}
		}
		return respSaveEye;
	}

	@Override
	public String removeEye(List<ReqRemoveEye> eyes) throws Exception {
		String eyeId = eyes.get(0).getEyeId();
		try {
			eyeMapper.removeEye(eyes);
		}
		catch(Exception e) {
			throw DaasException.of().setMsg("删除眼睛信息异常:" + e.getMessage());
		}
		
		return eyeId;
	}

	@Override
	public List<RespEyeInfo> eyesInfoByUser(String userId) {
		return eyeMapper.eyesInfoByUser(userId);
	}

	@Override
	public RespSaveEye insertEye(ReqSaveEye req) throws Exception {
		ReqSaveEye reqNew = new ReqSaveEye();
		RespSaveEye resp = new RespSaveEye();
		try {
			//眼睛ID
			String eyeId = req.getEyeId();
			
			BeanUtil.copyProperties(req, reqNew);
			//新增操作
			if (eyeId == null || eyeId.isEmpty()) {
				reqNew.setEyeId(IdUtil.fastSimpleUUID());
			}
			if(eyeMapper.insertEye(reqNew) > 0) {
				BeanUtil.copyProperties(reqNew, resp);
			}
		}
		catch(Exception e) {
			throw DaasException.of().setMsg("保存眼睛信息异常:" + e.getMessage());
		}
		
		return resp;
	}
}
