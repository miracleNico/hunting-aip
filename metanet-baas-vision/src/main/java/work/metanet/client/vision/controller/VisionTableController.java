package work.metanet.client.vision.controller;

import javax.validation.Valid;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import work.metanet.api.vision.IVisionTable;
import work.metanet.api.vision.protocol.ReqVisionTableInfo;
import work.metanet.api.vision.protocol.ReqVisionTableInfo.RespVisionTableInfo;
import work.metanet.base.Result;
import work.metanet.base.ResultMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Description 控制器：视力表
 * @author EdisonFeng
 * @DateTime 2021年5月1日
 * Copyright(c) 2021. All Rights Reserved
 */
@Api(tags={"视力表"})
@RestController
@RequestMapping("api/visionTable")
public class VisionTableController extends BaseController {
	@DubboReference
	private IVisionTable visionTableService;
	@ApiOperation(value="视力表查询")
	@PostMapping("visionTableInfo")
	public Result<RespVisionTableInfo> getVisionTable(@Valid @RequestBody ReqVisionTableInfo req) throws Exception {
		return ResultMessage.SUCCESS.result(visionTableService.getVisionTableInfo(req));
	}
}
