package work.metanet.client.vision.controller;

import work.metanet.api.vision.IVisionPlan;
import work.metanet.api.vision.protocol.*;
import work.metanet.base.RespPaging;
import work.metanet.base.Result;
import work.metanet.base.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author nicely
 * @Description
 * @date 2021年05月18日15:34
 */
@Api(tags = {"视力计划"})
@RestController
@RequestMapping("api/visionPlan")
public class VisionPlanController extends BaseController {

    @DubboReference
    private IVisionPlan visionPlanService;

    @ApiOperation(value = "视力计划列表")
    @PostMapping("visionPlanList")
    public Result<RespPaging<ReqVisionPlanList.RespVisionPlanList>> getVisionPlanList(@RequestBody ReqVisionPlanList req){
        RespPaging<ReqVisionPlanList.RespVisionPlanList> visionPlanList = new RespPaging<>();
        try {
            visionPlanList = visionPlanService.getVisionPlanList(req);
        } catch (Exception e) {
            return ResultMessage.FAILURE.result(visionPlanList).setMsg("未知异常");
        }
        return ResultMessage.SUCCESS.result(visionPlanList);
    }

    @ApiOperation(value = "视力计划详情")
    @PostMapping("visionPlanInfo")
    public Result<ReqVisionPlanInfo.RespVisionPlanInfo> getVisionPlanInfo(@Valid @RequestBody ReqVisionPlanInfo req){
        ReqVisionPlanInfo.RespVisionPlanInfo visionPlanInfo = new ReqVisionPlanInfo.RespVisionPlanInfo();
        try {
            visionPlanInfo = visionPlanService.getVisionPlanInfo(req);
        } catch (Exception e) {
            return ResultMessage.FAILURE.result(visionPlanInfo).setMsg("未知异常"+e.getMessage());
        }
        return ResultMessage.SUCCESS.result(visionPlanInfo);
    }

    @ApiOperation(value = "视力计划修改")
    @PostMapping("visionPlanUpdate")
    public Result<ReqSaveVisionPlan.RespSaveVisionPlan> updateVisionPlan(@Valid @RequestBody ReqSaveVisionPlan req){
        ReqSaveVisionPlan.RespSaveVisionPlan respSaveVisionPlan = new ReqSaveVisionPlan.RespSaveVisionPlan();
        try {
            respSaveVisionPlan = visionPlanService.saveVisionPlan(req);
        } catch (Exception e) {
            return ResultMessage.FAILURE.result(respSaveVisionPlan).setMsg("未知异常");
        }
        return ResultMessage.SUCCESS.result(respSaveVisionPlan);
    }

    @ApiOperation(value = "视力计划新增")
    @PostMapping("visionPlanInsert")
    public Result<ReqSaveVisionPlan.RespSaveVisionPlan> insertVisionPlan(@Valid @RequestBody ReqInsertVisionPlan req){
        ReqSaveVisionPlan.RespSaveVisionPlan respSaveVisionPlan = new ReqSaveVisionPlan.RespSaveVisionPlan();
        try {
            respSaveVisionPlan = visionPlanService.insertVisionPlan(req);
            System.out.println(respSaveVisionPlan);
        } catch (Exception e) {
            return ResultMessage.FAILURE.result(respSaveVisionPlan).setMsg("");
        }
        return ResultMessage.SUCCESS.result(respSaveVisionPlan);
    }

    @ApiOperation(value = "视力计划删除")
    @PostMapping("visionPlanRemove")
    public Result<?> removeVisionPlan(@Valid @RequestBody List<ReqRemoveVisionPlan> list){
        List<ReqRemoveVisionPlan> reqRemoveVisionPlans = null;
        try {
            reqRemoveVisionPlans = visionPlanService.removeVisionPlan(list);
        } catch (Exception e) {
            return ResultMessage.FAILURE.result().setMsg("未知异常");
        }
        return ResultMessage.SUCCESS.result(reqRemoveVisionPlans);
    }



}

