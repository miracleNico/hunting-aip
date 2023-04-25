package work.metanet.controller;

import com.github.pagehelper.Page;
import work.metanet.api.vision.IVisionExercise;
import work.metanet.api.vision.protocol.ReqRemoveVisionExercise;
import work.metanet.api.vision.protocol.ReqSaveVisionExercise;
import work.metanet.api.vision.protocol.ReqVisionExerciseInfo;
import work.metanet.api.vision.protocol.ReqVisionExerciseList;
import work.metanet.base.RespPaging;
import work.metanet.base.Result;
import work.metanet.base.ResultMessage;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author nicely
 * @Description
 * @date 2021年06月04日14:52
 */
@Controller
@RequestMapping("vision/exercise")
public class VisionExerciseController {

    @DubboReference
    private IVisionExercise visionExerciseService;

    @RequestMapping("list")
    public String list(){
        return "page/visionExercise/list";
    }

    @RequestMapping("insert")
    public String insert(){
        return "page/visionExercise/insert";
    }

    @RequestMapping("update/{exerciseId}")
    public String update(@PathVariable("exerciseId")String exerciseId, Model model) throws Exception {
        ReqVisionExerciseInfo.RespVisionExerciseInfo visionExerciseInfo = visionExerciseService.getVisionExerciseInfo(new ReqVisionExerciseInfo().setExerciseId(exerciseId));
        model.addAttribute("info",visionExerciseInfo);
        return "page/visionExercise/update";
    }

    @PostMapping("visionExerciseList")
    @ResponseBody
    public Result<RespPaging<ReqVisionExerciseList.RespVisionExerciseList>> getVisionExerciseList(@RequestBody ReqVisionExerciseList req){
        RespPaging<ReqVisionExerciseList.RespVisionExerciseList> visionExerciseList = new RespPaging<>();
        try {
            visionExerciseList = visionExerciseService.getVisionExerciseList(req);
        } catch (Exception e) {
            return ResultMessage.FAILURE.result(visionExerciseList).setMsg("未知异常");
        }
        return ResultMessage.SUCCESS.result(visionExerciseList);
    }

    @PostMapping("visionExerciseInfo")
    @ResponseBody
    public Result<ReqVisionExerciseInfo.RespVisionExerciseInfo> getVisionExerciseInfo(@Valid @RequestBody ReqVisionExerciseInfo req){
        ReqVisionExerciseInfo.RespVisionExerciseInfo visionExerciseInfo = new ReqVisionExerciseInfo.RespVisionExerciseInfo();
        try {
            visionExerciseInfo = visionExerciseService.getVisionExerciseInfo(req);
        } catch (Exception e) {
            return ResultMessage.FAILURE.result(visionExerciseInfo).setMsg("未知异常");
        }
        return ResultMessage.SUCCESS.result(visionExerciseInfo);
    }

    @PostMapping("updateVisionExercise")
    @ResponseBody
    public Result<ReqSaveVisionExercise.RespSaveVisionExercise> updateVisionExercise(@RequestBody ReqSaveVisionExercise req){
        ReqSaveVisionExercise.RespSaveVisionExercise respSaveVisionExercise = new ReqSaveVisionExercise.RespSaveVisionExercise();
        try {
            System.out.println("===========================>exerciseReq:"+req.getTimeDuration());
            respSaveVisionExercise = visionExerciseService.updateVisionExercise(req);
        } catch (Exception e) {
            return ResultMessage.FAILURE.result(respSaveVisionExercise).setMsg("未知异常"+e.getMessage());
        }
        return ResultMessage.SUCCESS.result(respSaveVisionExercise);
    }

    @PostMapping("insertVisionExercise")
    @ResponseBody
    public Result<ReqSaveVisionExercise.RespSaveVisionExercise> insertVisionExercise(@Valid @RequestBody ReqSaveVisionExercise req){
        ReqSaveVisionExercise.RespSaveVisionExercise respSaveVisionExercise = new ReqSaveVisionExercise.RespSaveVisionExercise();
        try {
            respSaveVisionExercise = visionExerciseService.insertVisionExercise(req);
        } catch (Exception e) {
            return ResultMessage.FAILURE.result(respSaveVisionExercise).setMsg("未知异常");
        }
        return ResultMessage.SUCCESS.result(respSaveVisionExercise);
    }

    @PostMapping("removeVisionExercise")
    @ResponseBody
    public Result<?> removeVisionExercise(@Valid @RequestBody List<ReqRemoveVisionExercise> req){
        List<ReqRemoveVisionExercise> reqRemoveVisionExercises = null;
        try {
            reqRemoveVisionExercises = visionExerciseService.removeVisionExercise(req);
        } catch (Exception e) {
            return ResultMessage.FAILURE.result().setMsg("未知异常");
        }
        return ResultMessage.SUCCESS.result(reqRemoveVisionExercises);
    }

}

