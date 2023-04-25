package work.metanet.server.service;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import work.metanet.api.sn.ISerialNumberService;
import work.metanet.api.sn.protocol.ReqRemoveSerialNumber;
import work.metanet.api.sn.protocol.ReqSaveSerialNumber;
import work.metanet.api.sn.protocol.ReqSerialNumberInfo;
import work.metanet.api.sn.protocol.ReqSerialNumberInfo.RespSerialNumberInfo;
import work.metanet.api.sn.protocol.ReqSerialNumberList;
import work.metanet.api.sn.protocol.ReqSerialNumberList.RespSerialNumberList;
import work.metanet.base.RespPaging;
import work.metanet.exception.DaasException;
import work.metanet.model.App;
import work.metanet.model.SerialNumber;
import work.metanet.server.dao.AppMapper;
import work.metanet.server.dao.SerialNumberMapper;
import work.metanet.utils.LxKeyUtil;

import cn.hutool.core.bean.BeanUtil;

@DubboService
public class SerialNumberService implements ISerialNumberService{

	@Autowired
	private SerialNumberMapper serialNumberMapper;
	@Autowired
	private AppMapper appMapper;
	
	/**
	 * @Description: SN码列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespPaging<RespSerialNumberList> serialNumberList(ReqSerialNumberList req) throws Exception {
		RespPaging<RespSerialNumberList> respPaging = new RespPaging<RespSerialNumberList>();
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<RespSerialNumberList> list = serialNumberMapper.serialNumberList(BeanUtil.beanToMap(req));
		BeanUtil.copyProperties(new PageInfo<RespSerialNumberList>(list), respPaging);
		return respPaging;
	}

	/**
	 * @Description: SN码信息
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespSerialNumberInfo serialNumberInfo(ReqSerialNumberInfo req) throws Exception {
		RespSerialNumberInfo resp = serialNumberMapper.serialNumberInfo(BeanUtil.beanToMap(req));
		return resp;
	}

	/**
	 * @Description: 保存SN码
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public void saveSerialNumber(ReqSaveSerialNumber req) throws Exception {
		SerialNumber serialNumber = new SerialNumber();
		if(BeanUtil.isEmpty(req.getSnCodeNum()) || req.getSnCodeNum()<1) req.setSnCodeNum(1);
		BeanUtil.copyProperties(req, serialNumber);
		
		if(StringUtils.isNotBlank(serialNumber.getSnCode())) {
			SerialNumber snDB = serialNumberMapper.selectByPrimaryKey(serialNumber);
			if(snDB.getUseStatus() && !snDB.getAppId().equals(req.getAppId())) throw DaasException.of().setMsg("使用过的激活码不能更换到其他产品上哦");
			if(req.getMaxUseNumber()<snDB.getUseNumber()) throw DaasException.of().setMsg("最大使用次数不能低于已使用次数");
			//修改操作
			serialNumberMapper.updateByPrimaryKeySelective(serialNumber);
		}else {
			App app = appMapper.selectByPrimaryKey(req.getAppId());
			if(!app.getEnableSn()) {
				int appSnNumber = serialNumberMapper.appSnNumber(req.getAppId());
				if(appSnNumber>0) throw DaasException.of().setMsg("此产品为禁用激活码激活,已经存在一个可用的激活码,不能再添加哦");
				if(appSnNumber==0 && req.getSnCodeNum()>1) throw DaasException.of().setMsg("此产品为禁用激活码激活,只允许添加1个可用的激活码哦");
			}
			//新增操作  《这里需要做对比,否则15位SN码日后可能出现重复》 必要时可使用@Async异步处理
			List<SerialNumber> serialNumbers = new ArrayList<SerialNumber>();
			for (int i = 1; i <= req.getSnCodeNum() ; i++) {
				SerialNumber snCopy = new SerialNumber();
				BeanUtil.copyProperties(serialNumber, snCopy);
				snCopy.setSnCode(LxKeyUtil.appSerialNumber());
				serialNumbers.add(snCopy);
			}
			serialNumberMapper.addSerialNumber(serialNumbers);
		}
	}
	
	
	/**
	 * @Description: 删除SN码
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public void removeSerialNumber(List<ReqRemoveSerialNumber> req) throws Exception {
		if(req.size()==1) {
			SerialNumber serialNumber = serialNumberMapper.getSerialNumber(req.get(0).getSnCode());
			if(serialNumber.getUseStatus())
				throw DaasException.of().setMsg("SN码已经被使用，不能删除！");
		}
		serialNumberMapper.removeSerialNumber(req);
	}
	
	
	
	
	
	
	
	
}
