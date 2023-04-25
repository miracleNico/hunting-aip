package work.metanet.client.vision.controller;

import work.metanet.api.vision.IVisionExercise;
import work.metanet.api.vision.protocol.ReqRemoveVisionExercise;
import work.metanet.api.vision.protocol.ReqSaveVisionExercise;
import work.metanet.api.vision.protocol.ReqVisionExerciseInfo;
import work.metanet.api.vision.protocol.ReqVisionExerciseList;
import work.metanet.base.RespPaging;
import work.metanet.base.Result;
import work.metanet.base.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author nicely
 * @Description
 * @date 2021年05月18日11:20
 */
@Api(tags = {"锻炼活动信息"})
@RestController
@RequestMapping("api/visionExercise")
public class VisionExerciseController extends BaseController {

    @DubboReference
    private IVisionExercise visionExerciseService;

    @ApiOperation(value = "锻炼活动信息列表")
    @PostMapping("visionExerciseList")
    public Result<RespPaging<ReqVisionExerciseList.RespVisionExerciseList>> getVisionExerciseList(@RequestBody ReqVisionExerciseList req){
        RespPaging<ReqVisionExerciseList.RespVisionExerciseList> visionExerciseList = new RespPaging<>();
        try {
            visionExerciseList = visionExerciseService.getVisionExerciseList(req);
        } catch (Exception e) {
            return ResultMessage.FAILURE.result(visionExerciseList).setMsg("未知异常");
        }
        return ResultMessage.SUCCESS.result(visionExerciseList);
    }

    @ApiOperation(value = "根据类型分组并排序锻炼活动信息列表")
    @PostMapping("visionExerciseListOfTheWeek")
    public Result<List<ReqVisionExerciseList.RespVisionExerciseList>> getVisionExerciseListGroupByTypeOrderByTime(@RequestBody ReqVisionExerciseList req) {
        List<ReqVisionExerciseList.RespVisionExerciseList> visionExerciseList = null;
        try {
            visionExerciseList = visionExerciseService.getVisionExerciseListGroupByTypeOrderByTime(req);
        } catch (Exception e) {
            return ResultMessage.FAILURE.result(visionExerciseList).setMsg("未知异常");
        }
        return ResultMessage.SUCCESS.result(visionExerciseList);
    }

    @ApiOperation(value = "锻炼活动信息")
    @PostMapping("visionExerciseInfo")
    public Result<ReqVisionExerciseInfo.RespVisionExerciseInfo> getVisionExerciseInfo(@Valid @RequestBody ReqVisionExerciseInfo req){
        ReqVisionExerciseInfo.RespVisionExerciseInfo visionExerciseInfo = new ReqVisionExerciseInfo.RespVisionExerciseInfo();
        try {
            visionExerciseInfo = visionExerciseService.getVisionExerciseInfo(req);
        } catch (Exception e) {
            return ResultMessage.FAILURE.result(visionExerciseInfo).setMsg("未知异常");
        }
        return ResultMessage.SUCCESS.result(visionExerciseInfo);
    }

//    @ApiOperation(value = "修改锻炼活动信息")
    @PostMapping("updateVisionExercise")
    public Result<ReqSaveVisionExercise.RespSaveVisionExercise> updateVisionExercise(@RequestBody ReqSaveVisionExercise req){
        ReqSaveVisionExercise.RespSaveVisionExercise respSaveVisionExercise = new ReqSaveVisionExercise.RespSaveVisionExercise();
        try {
            respSaveVisionExercise = visionExerciseService.updateVisionExercise(req);
        } catch (Exception e) {
            return ResultMessage.FAILURE.result(respSaveVisionExercise).setMsg("未知异常"+e.getMessage());
        }
        return ResultMessage.SUCCESS.result(respSaveVisionExercise);
    }

    @ApiOperation(value = "新增锻炼活动信息")
    @PostMapping("visionExercise")
    public Result<ReqSaveVisionExercise.RespSaveVisionExercise> insertVisionExercise(@Valid @RequestBody ReqSaveVisionExercise req){
        ReqSaveVisionExercise.RespSaveVisionExercise respSaveVisionExercise = new ReqSaveVisionExercise.RespSaveVisionExercise();
        try {
            respSaveVisionExercise = visionExerciseService.insertVisionExercise(req);
        } catch (Exception e) {
            return ResultMessage.FAILURE.result(respSaveVisionExercise).setMsg("未知异常");
        }
        return ResultMessage.SUCCESS.result(respSaveVisionExercise);
    }

//    @ApiOperation(value = "删除锻炼活动")
    @PostMapping("removeVisionExercise")
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

