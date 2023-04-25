package work.metanet.controller;

import javax.validation.Valid;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import work.metanet.api.userAddress.IUserAddressService;
import work.metanet.api.userAddress.protocol.ReqSaveUserAddress;
import work.metanet.base.BaseController;
import work.metanet.base.Result;
import work.metanet.base.ResultMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags={"用户收货地址"})
@RestController
@RequestMapping("api/userAddress")
public class UserAddressController extends BaseController{
	
	@DubboReference
	private IUserAddressService userAddressService;
	
	@ApiOperation(value="收货地址信息")
	@PostMapping("userAddressInfo")
	public Result<?> userAddressInfo() throws Exception {
		return ResultMessage.SUCCESS.result(userAddressService.userAddressInfo(getUserId()));
	}
	
	@ApiOperation(value="保存收货地址")
	@PostMapping("saveUserAddress")
	public Result<?> saveUserAddress(@Valid @RequestBody ReqSaveUserAddress req) throws Exception {
		userAddressService.saveUserAddress(getUserId(), req);
		return ResultMessage.SUCCESS.result();
	}
	
}
