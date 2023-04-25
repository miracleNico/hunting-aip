package work.metanet.controller;

import work.metanet.aop.ApiOperLog;
import work.metanet.api.vision.IEyestrain;
import work.metanet.api.vision.protocol.*;
import work.metanet.base.RespPaging;
import work.metanet.base.Result;
import work.metanet.base.ResultMessage;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author nicely
 * @Description
 * @date 2021年06月03日14:21
 */
@Controller
@RequestMapping("vision/eyestrain")
public class EyestrainController {

    @DubboReference
    private IEyestrain eyestrainService;


    @RequestMapping("eyestrainList")
    public String planList() {
        return "page/visionEyestrain/list";
    }

    @RequestMapping("insert")
    public String planInsert() {
        return "page/visionEyestrain/insert";
    }

    @RequestMapping("update/{eyestrainId}")
    public String planUpdate(@PathVariable("eyestrainId") String eyestrainId, Model model) throws Exception {
        Result<?> info = getEyestrainInfo(new ReqEyestrainInfo().setEyestrainId(eyestrainId));
        model.addAttribute("info",info.getData());
        return "page/visionEyestrain/update";
    }

    @PostMapping("userEyestrain")
    @ResponseBody
    public Result<?> getEyestrainInfo(@Valid @RequestBody ReqEyestrainInfo req) throws Exception {
        ReqEyestrainInfo.RespEyestrainInfo eyestrainInfo = new ReqEyestrainInfo.RespEyestrainInfo();
        try {
            eyestrainInfo =  eyestrainService.getEyestrainInfo(req);
        } catch (Exception e) {
            return ResultMessage.FAILURE.result().setMsg("未知异常");
        }

        return ResultMessage.SUCCESS.result(eyestrainInfo);
    }

    @PostMapping("insertEyestrain")
    @ResponseBody
    public Result<ReqSaveEyestrain.RespSaveEyestrain> insertEyestrainInfo(@RequestBody ReqSaveEyestrain req) throws Exception {
        ReqSaveEyestrain.RespSaveEyestrain resp = new ReqSaveEyestrain.RespSaveEyestrain();
        try {
            resp = eyestrainService.insertEyestrain(req);
        } catch (Exception e) {
            return ResultMessage.FAILURE.result(resp).setMsg("未知异常");
        }
        return ResultMessage.SUCCESS.result(resp);
    }

    @PostMapping("updateEyestrain")
    @ResponseBody
    public Result<ReqSaveEyestrain.RespSaveEyestrain> updateEyestrainInfo(@RequestBody ReqSaveEyestrain req) throws Exception {
        ReqSaveEyestrain.RespSaveEyestrain resp = new ReqSaveEyestrain.RespSaveEyestrain();
        try {
            resp = eyestrainService.updateEyestrain(req);
        } catch (Exception e) {
            return ResultMessage.FAILURE.result(resp).setMsg("未知异常");
        }
        return ResultMessage.SUCCESS.result(resp);
    }

    @ApiOperLog(action = ApiOperLog.ACTION.DELETE)
    @PostMapping("removeEyestrain")
    @ResponseBody
    public Result<String> removeEyestrain(@Valid @RequestBody List<ReqRemoveEyestrain> req) throws Exception {
        String resp = null;
        try {
            resp = eyestrainService.removeEyestrain(req);
        } catch (Exception e) {
            return ResultMessage.FAILURE.result(resp).setMsg("未知异常");
        }
        return ResultMessage.SUCCESS.result(resp);
    }

    @PostMapping("getEyestrainList")
    @ResponseBody
    public Result<RespPaging<ReqEyestrainList.RespEyestrainList>> getEyestrainList(@RequestBody ReqEyestrainList req) throws Exception {
        RespPaging<ReqEyestrainList.RespEyestrainList> resp = null;
        try {
            resp = eyestrainService.getEyestrainList(req);
        } catch (Exception e) {
            return ResultMessage.FAILURE.result(resp).setMsg("未知异常");
        }
        return ResultMessage.SUCCESS.result(resp);
    }

}

