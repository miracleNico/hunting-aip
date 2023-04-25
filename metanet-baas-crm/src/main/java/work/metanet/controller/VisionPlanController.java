package work.metanet.controller;

import work.metanet.aop.ApiModule;
import work.metanet.api.vision.IVisionPlan;
import work.metanet.api.vision.protocol.*;
import work.metanet.base.RespPaging;
import work.metanet.base.Result;
import work.metanet.base.ResultMessage;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

/**
 * @author nicely
 * @Description
 * @date 2021年06月01日15:13
 */
@ApiModule(module = ApiModule.Module.VISION_PLAN)
@Controller
@RequestMapping("vision")
public class VisionPlanController {

    @DubboReference
    private IVisionPlan visionPlanService;

    @RequestMapping("plan/planListPage")
    public String planList() {
        return "page/visionPlan/planList";
    }

    @RequestMapping("plan/info")
    public String planInfo() {
        return "page/visionPlan/info";
    }

    @RequestMapping("plan/insert")
    public String planInsert() {
        return "page/visionPlan/insert";
    }

    @RequestMapping("plan/update")
    public String planUpdate(String planId,Model model) {
        Result<ReqVisionPlanInfo.RespVisionPlanInfo> info = getVisionPlanInfo(new ReqVisionPlanInfo().setPlanId(planId));
        model.addAttribute("info",info.getData());
        return "page/visionPlan/update";
    }

    @PostMapping("plan/planList")
    @ResponseBody
    public Result<RespPaging<ReqVisionPlanList.RespVisionPlanList>> getVisionPlanList(@RequestBody ReqVisionPlanList req) {
        RespPaging<ReqVisionPlanList.RespVisionPlanList> visionPlanList = new RespPaging<>();
        try {
            visionPlanList = visionPlanService.getVisionPlanList(req);
        } catch (Exception e) {
            return ResultMessage.FAILURE.result(visionPlanList).setMsg("未知异常");
        }
        return ResultMessage.SUCCESS.result(visionPlanList);
    }

    @PostMapping("plan/visionPlanInfo")
    @ResponseBody
    public Result<ReqVisionPlanInfo.RespVisionPlanInfo> getVisionPlanInfo(@Valid @RequestBody ReqVisionPlanInfo req) {
        ReqVisionPlanInfo.RespVisionPlanInfo visionPlanInfo = new ReqVisionPlanInfo.RespVisionPlanInfo();
        try {
            visionPlanInfo = visionPlanService.getVisionPlanInfo(req);
        } catch (Exception e) {
            return ResultMessage.FAILURE.result(visionPlanInfo).setMsg("未知异常" + e.getMessage());
        }
        return ResultMessage.SUCCESS.result(visionPlanInfo);
    }

    @PostMapping("plan/visionPlanUpdate")
    @ResponseBody
    public Result<ReqSaveVisionPlan.RespSaveVisionPlan> updateVisionPlan(@Valid @RequestBody ReqSaveVisionPlan req) {
        ReqSaveVisionPlan.RespSaveVisionPlan respSaveVisionPlan = new ReqSaveVisionPlan.RespSaveVisionPlan();
        try {
            respSaveVisionPlan = visionPlanService.saveVisionPlan(req);
        } catch (Exception e) {
            return ResultMessage.FAILURE.result(respSaveVisionPlan).setMsg("未知异常");
        }
        return ResultMessage.SUCCESS.result(respSaveVisionPlan);
    }

    @PostMapping("plan/visionPlanInsert")
    @ResponseBody
    public Result<ReqSaveVisionPlan.RespSaveVisionPlan> insertVisionPlan(@Valid @RequestBody ReqInsertVisionPlan req) {
        ReqSaveVisionPlan.RespSaveVisionPlan respSaveVisionPlan = new ReqSaveVisionPlan.RespSaveVisionPlan();
        try {
            respSaveVisionPlan = visionPlanService.insertVisionPlan(req);
            System.out.println(respSaveVisionPlan);
        } catch (Exception e) {
            return ResultMessage.FAILURE.result(respSaveVisionPlan).setMsg("");
        }
        return ResultMessage.SUCCESS.result(respSaveVisionPlan);
    }

    @PostMapping("plan/visionPlanRemove")
    @ResponseBody
    public Result<?> removeVisionPlan(@Valid @RequestBody List<ReqRemoveVisionPlan> list) {
        List<ReqRemoveVisionPlan> reqRemoveVisionPlans = null;
        try {
            reqRemoveVisionPlans = visionPlanService.removeVisionPlan(list);
        } catch (Exception e) {
            return ResultMessage.FAILURE.result().setMsg("未知异常");
        }
        return ResultMessage.SUCCESS.result(reqRemoveVisionPlans);
    }


}

