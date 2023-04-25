
package work.metanet.client.vision.controller;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.validation.Valid;

import cn.hutool.json.JSONUtil;
import work.metanet.exception.LxException;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import work.metanet.aop.ApiOperLog;
import work.metanet.aop.ApiOperLog.ACTION;
import work.metanet.api.user.IUserService;
import work.metanet.api.vision.IEyestrain;
import work.metanet.api.vision.protocol.ReqRemoveEyestrain;
import work.metanet.api.vision.protocol.ReqEyestrainInfo;
import work.metanet.api.vision.protocol.ReqEyestrainInfo.RespEyestrainInfo;
import work.metanet.api.vision.protocol.ReqEyestrainList;
import work.metanet.api.vision.protocol.ReqEyestrainList.RespEyestrainList;
import work.metanet.api.vision.protocol.ReqSaveEyestrain;
import work.metanet.api.vision.protocol.ReqSaveEyestrain.RespSaveEyestrain;
import work.metanet.base.RespPaging;
import work.metanet.base.Result;
import work.metanet.base.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author EdisonFeng
 * @Description 控制类：用眼活动
 * @DateTime 2021年5月17日
 * Copyright(c) 2021. All Rights Reserved
 */
@Api(tags = {"用眼活动信息"})
@RestController
@RequestMapping("api/userEyestrain")
public class EyestrainController extends BaseController {
    @DubboReference
    private IEyestrain eyestrainService;
    @DubboReference
    private IUserService userService;
    private RespEyestrainInfo respEyestrainInfo;

    @ApiOperation(value = "用户眼睛信息查询")
    @PostMapping("userEyestrain")
    public Result<?> getEyestrainInfo(@Valid @RequestBody ReqEyestrainInfo req) throws Exception {
        if (StringUtils.isBlank(req.getUserId()) && StringUtils.isBlank(req.getEyestrainId())) {
            return ResultMessage.FAILURE.result().setMsg("userId和eyestrainId不能同时为空");
        }
        List<RespEyestrainInfo> resp;
        try {
            resp = eyestrainService.getUserEyestrainInfo(req);
        } catch (Exception e) {
            return ResultMessage.FAILURE.result().setMsg("未知异常");
        }

        return ResultMessage.SUCCESS.result(resp);
    }

    @ApiOperation(value = "新增信息")
    @PostMapping("insertEyestrain")
    public Result<RespSaveEyestrain> insertEyestrainInfo(@RequestBody ReqSaveEyestrain req) throws Exception {
        RespSaveEyestrain resp = new RespSaveEyestrain();
        try {
            resp = eyestrainService.insertEyestrain(req);
        } catch (Exception e) {
            return ResultMessage.FAILURE.result(resp).setMsg("未知异常");
        }
        return ResultMessage.SUCCESS.result(resp);
    }

    @ApiOperation(value = "修改信息")
    @PostMapping("updateEyestrain")
    public Result<RespSaveEyestrain> updateEyestrainInfo(@RequestBody ReqSaveEyestrain req) throws Exception {
        RespSaveEyestrain resp = new RespSaveEyestrain();
        try {
            resp = eyestrainService.updateEyestrain(req);
        } catch (Exception e) {
            return ResultMessage.FAILURE.result(resp).setMsg("未知异常");
        }
        return ResultMessage.SUCCESS.result(resp);
    }

    @ApiOperation(value = "删除信息")
    @ApiOperLog(action = ACTION.DELETE)
    @PostMapping("removeEyestrain")
    public Result<String> removeEyestrain(@Valid @RequestBody List<ReqRemoveEyestrain> req) throws Exception {
        String resp = null;
        try {
            resp = eyestrainService.removeEyestrain(req);
        } catch (Exception e) {
            e.printStackTrace();
            String msg = e.getMessage();
            if (StringUtils.isBlank(msg)) msg = "未知异常";
            return ResultMessage.FAILURE.result(resp).setMsg(msg);
        }
        return ResultMessage.SUCCESS.result(resp);
    }

    @ApiOperation(value = "获取信息列表")
    @PostMapping("getEyestrainList")
    public Result<RespPaging<RespEyestrainList>> getEyestrainList(@RequestBody ReqEyestrainList req) throws Exception {
        RespPaging<RespEyestrainList> resp = null;
        try {
            resp = eyestrainService.getEyestrainList(req);
        } catch (Exception e) {
            String msg = e.getMessage();
            if (StringUtils.isBlank(msg)) msg = "未知异常";
            return ResultMessage.FAILURE.result(resp).setMsg(msg);
        }
        return ResultMessage.SUCCESS.result(resp);
    }

    @ApiOperation(value = "获取信息列表分组排序")
    @PostMapping("getEyestrainListOrderByUseTypeAndGroupByTime")
    public Result<List<RespEyestrainList>> getEyestrainListOrderByUseTypeAndGroupByTime(@RequestBody ReqEyestrainList req) throws Exception {
        List<RespEyestrainList> resp = null;
        try {
            resp = eyestrainService.getEyestrainListOrderByUseTypeAndGroupByTime(req);
        } catch (Exception e) {
            String msg = e.getMessage();
            if (StringUtils.isBlank(msg)) msg = "未知异常";
            return ResultMessage.FAILURE.result(resp).setMsg(msg);
        }
        return ResultMessage.SUCCESS.result(resp);
    }
}
