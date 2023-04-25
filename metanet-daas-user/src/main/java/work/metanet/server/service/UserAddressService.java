package work.metanet.server.service;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import work.metanet.api.userAddress.IUserAddressService;
import work.metanet.api.userAddress.protocol.ReqSaveUserAddress;
import work.metanet.api.userAddress.protocol.ReqSaveUserAddress.RespSaveUserAddress;
import work.metanet.api.userAddress.protocol.ReqUserAddressInfo.RespUserAddressInfo;
import work.metanet.model.UserAddress;
import work.metanet.server.dao.UserAddressMapper;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;

@DubboService
public class UserAddressService implements IUserAddressService{
	
	@Autowired
	private UserAddressMapper userAddressMapper;
	
	@Override
	public RespSaveUserAddress saveUserAddress(String userId, ReqSaveUserAddress req) throws Exception {
		UserAddress userAddress = BeanUtil.copyProperties(req, UserAddress.class);
		if(StrUtil.isBlank(req.getUserAddressId())) {
			userAddress.setUserAddressId(IdUtil.fastSimpleUUID());
			userAddress.setUserId(userId);
			userAddressMapper.insertSelective(userAddress);
		}else {
			userAddressMapper.updateByPrimaryKeySelective(userAddress);
		}
		return BeanUtil.copyProperties(userAddress, RespSaveUserAddress.class);
	}
	
	@Override
	public RespUserAddressInfo userAddressInfo(String userId) throws Exception {
		return BeanUtil.copyProperties(userAddressMapper.userAddressInfo(userId), RespUserAddressInfo.class);
	}
	
}
