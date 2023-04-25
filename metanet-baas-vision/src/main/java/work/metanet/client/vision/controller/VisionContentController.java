package work.metanet.client.vision.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import work.metanet.aop.ApiModule;
import work.metanet.aop.ApiOperLog;
import work.metanet.api.visionContent.IVisionContentService;
import work.metanet.api.visionContent.protocol.*;
import work.metanet.base.RespPaging;
import work.metanet.base.Result;
import work.metanet.base.ResultMessage;
import work.metanet.utils.ValidList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.logging.Logger;

@Api(tags = "护眼内容")
@ApiModule(module = ApiModule.Module.EYES_ARTICLE)
@Controller
@RequestMapping("api/eyesArticle")
public class VisionContentController extends BaseController {

    Logger log = Logger.getLogger("VisionContentController");

    @DubboReference
    private IVisionContentService eyesArticleService;

    @RequestMapping("listPage")
    public String listPage() {
        return "page/visionContent/list";
    }

    @RequestMapping("add")
    public String toAdd() {
        return "add";
    }

    @ApiOperation(value = "内容列表")
    @ApiOperLog(action = ApiOperLog.ACTION.SELECT)
    @ResponseBody
    @PostMapping("eyesArticleList")
    public Result<RespPaging<ReqVisionContentList.RespVisionContentList>> eyesArticleList(@RequestBody ReqVisionContentList req) throws Exception {
        return ResultMessage.SUCCESS.result(eyesArticleService.visionContentList(req));
    }

    @ApiOperation(value = "内容信息")
    @ApiOperLog(action = ApiOperLog.ACTION.INFO)
    @ResponseBody
    @PostMapping("eyesArticleInfo")
    public Result<ReqVisionContentInfo.RespVisionContentInfo> eyesArticleInfo(@Valid @RequestBody ReqVisionContentInfo req) throws Exception {
        ReqVisionContentInfo.RespVisionContentInfo resp = eyesArticleService.visionContentInfo(req);
        return ResultMessage.SUCCESS.result(resp);
    }


//    @ApiOperation(value = "修改内容")
    @ApiOperLog(action = ApiOperLog.ACTION.UPDATE)
    @ResponseBody
    @PostMapping("updateEyesArticle")
    public Result<?> updateEyesArticle(@Valid @RequestBody ReqUpdateVisionContent req) throws Exception {
        ReqUpdateVisionContent.RespUpdateVisionContent respUpdateVisionContent = eyesArticleService.updateVisionContent(req);
        if (respUpdateVisionContent == null) {
            return ResultMessage.FAILURE.result().setMsg("修改异常");
        }
        return ResultMessage.SUCCESS.result(respUpdateVisionContent);
    }

    @ApiOperation(value = "添加内容")
    @ApiOperLog(action = ApiOperLog.ACTION.ADD)
    @ResponseBody
    @PostMapping("insertEyesArticle")
    public Result<?> insertEyesArticle(@Valid @RequestBody ReqVisionContentInfo req) throws Exception {
        ReqVisionContentInfo.RespVisionContentInfo respVisionContentInfo = eyesArticleService.insertVisionContent(req);
        if (respVisionContentInfo == null) {
            return ResultMessage.FAILURE.result().setMsg("新增异常");
        }
        return ResultMessage.SUCCESS.result(respVisionContentInfo);
    }

//    @ApiOperation(value = "删除内容")
    @ApiOperLog(action = ApiOperLog.ACTION.DELETE)
    @ResponseBody
    @PostMapping("removeEyesArticle")
    public Result<?> removeEyesArticle(@Valid @RequestBody List<ReqRemoveVisionContent> req) throws Exception {
        List<ReqRemoveVisionContent.RespRemoveVisionContent> respRemoveVisionContents = eyesArticleService.removeVisionContents(req);
        log.info(new Gson().toJson(respRemoveVisionContents));
        if (respRemoveVisionContents == null) {
            return ResultMessage.FAILURE.result().setMsg("删除异常");
        }
        return ResultMessage.SUCCESS.result(respRemoveVisionContents);
    }


    @ApiOperation(value = "获取随机内容")
    @ApiOperLog(action = ApiOperLog.ACTION.SELECT)
    @ResponseBody
    @PostMapping("getRandomContent")
    public Result<ReqVisionContentInfo.RespVisionContentInfo> getRandomContent(@Valid @RequestBody List<ReqVisionContentInfo> infos) throws Exception {
        ReqVisionContentInfo.RespVisionContentInfo respEyesArticleInfos = eyesArticleService.ranContent(infos);
        if (respEyesArticleInfos == null) {
            return ResultMessage.FAILURE.result(respEyesArticleInfos).setMsg("内容获取失败");
        }
        return ResultMessage.SUCCESS.result(respEyesArticleInfos);
    }

    @ApiOperation(value = "动态获取锻炼类型")
    @ResponseBody
    @PostMapping("getExerciseType")
    public Result<List<ReqVisionContentInfo.RespVisionContentInfo>> getExerciseType()throws Exception{
        List<ReqVisionContentInfo.RespVisionContentInfo> exerciseType = eyesArticleService.getExerciseType();
        if (exerciseType == null || exerciseType.size()<=0){
            return ResultMessage.FAILURE.result(exerciseType).setMsg("未知异常");
        }
        return ResultMessage.SUCCESS.result(exerciseType);
    }

}
