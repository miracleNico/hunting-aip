package work.metanet.server.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import work.metanet.api.app.IAppService;
import work.metanet.api.upgradePlan.IUpgradePlanService;
import work.metanet.api.upgradePlan.protocol.ReqRemoveUpgradePlan;
import work.metanet.api.upgradePlan.protocol.ReqSaveUpgradePlan;
import work.metanet.api.upgradePlan.protocol.ReqSendUpgradePlan;
import work.metanet.api.upgradePlan.protocol.ReqUpgradePlanInfo;
import work.metanet.api.upgradePlan.protocol.ReqUpgradePlanInfo.RespUpgradePlanInfo;
import work.metanet.api.upgradePlan.protocol.ReqUpgradePlanList;
import work.metanet.api.upgradePlan.protocol.ReqUpgradePlanList.RespUpgradePlanList;
import work.metanet.api.upgradePlan.protocol.ReqUpgradePlanScopeInfo.RespUpgradePlanScopeInfo;
import work.metanet.base.RespPaging;
import work.metanet.base.ResultMessage;
import work.metanet.constant.ConstUpgradeScope;
import work.metanet.exception.DaasException;
import work.metanet.model.App;
import work.metanet.model.UpgradePlan;
import work.metanet.model.UpgradePlanScope;
import work.metanet.server.dao.AppMapper;
import work.metanet.server.dao.UpgradePlanMapper;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;

@DubboService
public class UpgradePlanService implements IUpgradePlanService{

	@Autowired
	private UpgradePlanMapper upgradePlanMapper;
	@Autowired
	private AppMapper appMapper;
	@Autowired
	private IAppService appService;
	
	/**
	 * @Description: 升级计划列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespPaging<RespUpgradePlanList> upgradePlanList(ReqUpgradePlanList req) throws Exception {
		RespPaging<RespUpgradePlanList> respPaging = new RespPaging<RespUpgradePlanList>();
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<RespUpgradePlanList> list = upgradePlanMapper.upgradePlanList(BeanUtil.beanToMap(req));
		for (RespUpgradePlanList respUpgradePlanList : list) {
			if(respUpgradePlanList.getDeviceScope().equals(ConstUpgradeScope.SPECIFIED.getVal())) {
				List<RespUpgradePlanScopeInfo> upgradePlanScopeInfos = upgradePlanMapper.upgradePlanScopeInfoList(respUpgradePlanList.getUpgradePlanId());
				respUpgradePlanList.setUpgradePlanScopeInfoList(upgradePlanScopeInfos);
			}
		}
		BeanUtil.copyProperties(new PageInfo<RespUpgradePlanList>(list), respPaging);
		return respPaging;
	}

	/**
	 * @Description: 升级计划信息
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespUpgradePlanInfo upgradePlanInfo(ReqUpgradePlanInfo req) throws Exception {
		RespUpgradePlanInfo resp = upgradePlanMapper.upgradePlanInfo(BeanUtil.beanToMap(req));
		if(resp.getDeviceScope().equals(ConstUpgradeScope.SPECIFIED.getVal())) {
			List<RespUpgradePlanScopeInfo> upgradePlanScopeInfos = upgradePlanMapper.upgradePlanScopeInfoList(resp.getUpgradePlanId());
			resp.setUpgradePlanScopeInfoList(upgradePlanScopeInfos);
		}
		return resp;
	}

	/**
	 * @Description: 保存升级计划
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	@Transactional
	public void saveUpgradePlan(ReqSaveUpgradePlan req) throws Exception {
		
		if(ConstUpgradeScope.SPECIFIED.getVal().equals(req.getDeviceScope()) && CollectionUtil.isEmpty(req.getUpgradePlanScopeInfoList()))
			throw DaasException.of().setMsg("指定范围不能为空，请选择！");
		
		UpgradePlan upgradePlan = new UpgradePlan();
		BeanUtil.copyProperties(req, upgradePlan);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("upgradePlanName", req.getUpgradePlanName());
		UpgradePlan dbUpgradePlan = upgradePlanMapper.getUpgradePlan(map);
		
		if(StringUtils.isNotBlank(upgradePlan.getUpgradePlanId())) {
			//修改操作
			if(!BeanUtil.isEmpty(dbUpgradePlan) && !dbUpgradePlan.getUpgradePlanId().equals(upgradePlan.getUpgradePlanId()))
				throw DaasException.of().setMsg("名称已存在");
			if(BeanUtil.isEmpty(dbUpgradePlan) || dbUpgradePlan.getUpgradePlanId().equals(upgradePlan.getUpgradePlanId())) {
				upgradePlanMapper.updateByPrimaryKeySelective(upgradePlan);
				resetUpgradePlanScope(req.getUpgradePlanScopeInfoList(), upgradePlan.getUpgradePlanId());
			}else {
				throw DaasException.of().setResult(ResultMessage.FAILURE.result());
			}
		}else {
			//新增操作
			if(!BeanUtil.isEmpty(dbUpgradePlan)) throw DaasException.of().setMsg("名称已存在");
			
			String upgradePlanId = IdUtil.fastSimpleUUID();
			upgradePlan.setUpgradePlanId(upgradePlanId);
			upgradePlanMapper.insertSelective(upgradePlan);
			resetUpgradePlanScope(req.getUpgradePlanScopeInfoList(), upgradePlanId);
		}
		
		App app = appMapper.selectByPrimaryKey(upgradePlan.getAppId());
		appService.clearAppUpgradeCache(app.getPackageName());
	}
	
	private void resetUpgradePlanScope(List<RespUpgradePlanScopeInfo> list,String upgradePlanId) {
		if(CollectionUtil.isNotEmpty(list)) {
			upgradePlanMapper.removeUpgradePlanScope(upgradePlanId);
			List<UpgradePlanScope> upgradePlanScopes = JSONUtil.toList(new JSONArray(list), UpgradePlanScope.class);
			for (UpgradePlanScope upgradePlanScope : upgradePlanScopes) {
				upgradePlanScope.setUpgradePlanScopeId(IdUtil.fastSimpleUUID());
				upgradePlanScope.setUpgradePlanId(upgradePlanId);
			}
			upgradePlanMapper.addUpgradePlanScope(upgradePlanScopes);
		}
	}
	
	/**
	 * @Description: 删除升级计划
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public void removeUpgradePlan(List<ReqRemoveUpgradePlan> req) throws Exception {
		for (ReqRemoveUpgradePlan o : req) {
			UpgradePlan upgradePlan = upgradePlanMapper.selectByPrimaryKey(o.getUpgradePlanId());
			App app = appMapper.selectByPrimaryKey(upgradePlan.getAppId());
			if(BeanUtil.isNotEmpty(app)) {
				appService.clearAppUpgradeCache(app.getPackageName());				
			}
		}
		upgradePlanMapper.removeUpgradePlan(req);
	}
	
	
	/**
	 * @Description: 发布或取消发布升级计划
	 * @Author wanbo
	 * @DateTime 2019/11/23
	 */
	@Override
	public void sendUpgradePlan(ReqSendUpgradePlan req) throws Exception {
		UpgradePlan upgradePlan = upgradePlanMapper.selectByPrimaryKey(req.getUpgradePlanId());
		if(BeanUtil.isEmpty(upgradePlan)) throw DaasException.of().setResult(ResultMessage.FAILURE.result());
		upgradePlanMapper.sendUpgradePlan(req.getUpgradePlanId(), req.getSendStatus());
		App app = appMapper.selectByPrimaryKey(upgradePlan.getAppId());
		appService.clearAppUpgradeCache(app.getPackageName());
	}
	
	
	
	
	
}
