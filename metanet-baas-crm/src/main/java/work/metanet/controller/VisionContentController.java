package work.metanet.controller;


import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import work.metanet.aop.ApiModule;
import work.metanet.aop.ApiOperLog;
import work.metanet.api.visionContent.IVisionContentService;
import work.metanet.api.visionContent.protocol.*;
import work.metanet.base.*;
import work.metanet.exception.LxException;
import work.metanet.model.VisionContentBase;
import work.metanet.utils.CosUtil;
import work.metanet.utils.ValidList;
import feign.Param;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.*;
import java.util.List;

@ApiModule(module = ApiModule.Module.EYES_ARTICLE)
@Controller
@RequestMapping("api/eyesArticle")
@Slf4j
public class VisionContentController extends BaseController {

    @DubboReference
    private IVisionContentService eyesArticleService;

    @Autowired
    private CosUtil cosUtil;

    @RequestMapping("listPage")
    public String listPage() {
        log.debug("listPage 进入");
        return "page/visionContent/list";
    }

    @RequestMapping("editPage/{contentId}")
    public String editPage(@PathVariable("contentId") String contentId, Model model) throws Exception {
        Result<ReqVisionContentInfo.RespVisionContentInfo> info = eyesArticleInfo(new ReqVisionContentInfo().setContentId(contentId));
        model.addAttribute("info", info);
        return "page/visionContent/edit";
    }

    @RequestMapping("toAdd")
    public String toAdd() {
        return "page/visionContent/add";
    }

    @RequestMapping("editContent")
    public String editContent() {
        return "page/visionContent/edit";
    }

    @RequestMapping("eyesArticleEditPage")
    public String insertEditPage() {
        return "page/visionContent/insertEditPage";
    }

    @ApiOperLog(action = ApiOperLog.ACTION.SELECT)
    @ResponseBody
    @PostMapping("eyesArticleList")
    public Result<RespPaging<ReqVisionContentList.RespVisionContentList>> eyesArticleList(@RequestBody ReqVisionContentList req) throws Exception {
        log.debug("eyesArticleList 进入");
        return ResultMessage.SUCCESS.result(eyesArticleService.visionContentList(req));
    }

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

    @ApiOperLog(action = ApiOperLog.ACTION.INFO)
    @ResponseBody
    @PostMapping("eyesArticleInfo")
    public Result<ReqVisionContentInfo.RespVisionContentInfo> eyesArticleInfo(@Valid @RequestBody ReqVisionContentInfo req) throws Exception {
        ReqVisionContentInfo.RespVisionContentInfo resp = eyesArticleService.visionContentInfo(req);
        return ResultMessage.SUCCESS.result(resp);
    }

    @ApiOperLog(action = ApiOperLog.ACTION.ADD)
    @ResponseBody
    @PostMapping("insertEyesArticle")
    public Result<?> insertEyesArticle(@RequestBody ReqVisionContentInfo req) throws Exception {
//        uploadHtml(req)
        ReqVisionContentInfo.RespVisionContentInfo respVisionContentInfo = eyesArticleService.insertVisionContent(req);
        if (respVisionContentInfo == null) {
            return ResultMessage.FAILURE.result().setMsg("未知异常");
        }
        return ResultMessage.SUCCESS.result(respVisionContentInfo);
    }

    @ApiOperLog(action = ApiOperLog.ACTION.DELETE)
    @ResponseBody
    @PostMapping("removeEyesArticle")
    public Result<?> removeEyesArticle(@Valid @RequestBody ValidList<ReqRemoveVisionContent> req) throws Exception {
        List<ReqRemoveVisionContent.RespRemoveVisionContent> respRemoveVisionContents = eyesArticleService.removeVisionContents(req);
        if (respRemoveVisionContents == null) {
            return ResultMessage.FAILURE.result().setMsg("删除异常");
        }
        return ResultMessage.SUCCESS.result(respRemoveVisionContents);
    }

    @ResponseBody
    @RequestMapping("uploadImg")
    public Result<RespUpload> uploadImage(MultipartFile file) {
        RespUpload resp = new RespUpload();
        String filename = file.getOriginalFilename();
        System.out.println(filename);
        String key = StrUtil.concat(true, "contentImg/", IdUtil.fastSimpleUUID(), filename.substring(filename.lastIndexOf(".")));
        try {
            String src = cosUtil.upload(key, file);
            resp.setSrc(src);
        } catch (Exception e) {
            return ResultMessage.FAILURE.result(resp).setMsg("上传失败");
        }
        return ResultMessage.SUCCESS.result(resp).setCode(0);
    }

    @ResponseBody
    @RequestMapping("uploadVideo")
    public Result<RespUpload> uploadVideo(MultipartFile file) {
        RespUpload resp = new RespUpload();
        String filename = file.getOriginalFilename();
        String key = StrUtil.concat(true, "contentVideo/", IdUtil.fastSimpleUUID(), filename.substring(filename.lastIndexOf(".")));
        try {
            resp.setSrc(cosUtil.upload(key, file));
        } catch (Exception e) {
            return ResultMessage.FAILURE.result(resp).setMsg("上传失败");
        }
        return ResultMessage.SUCCESS.result(resp);
    }

    @ResponseBody
    @RequestMapping("uploadAudio")
    public Result<RespUpload> uploadAudio(MultipartFile file) {
        RespUpload resp = new RespUpload();
        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));
        if (!suffix.equals(".mp3") && !suffix.equals(".wav")) {
            System.out.println(suffix);
            return ResultMessage.FAILURE.result(resp).setMsg("请上传mp3格式音频或wav格式");
        }
        String key = StrUtil.concat(true, "contentAudio/", IdUtil.fastSimpleUUID(), filename.substring(filename.lastIndexOf(".")));
        try {
            resp.setSrc(cosUtil.upload(key, file));
        } catch (Exception e) {
            return ResultMessage.FAILURE.result(resp).setMsg("上传失败");
        }
        return ResultMessage.SUCCESS.result(resp);
    }


    @ResponseBody
    @RequestMapping("deleteFile")
    public Result<RespUpload> deleteFile(@Param("imgpath") String imgpath, @Param("filepath") String filepath) {
        RespUpload respUpload = new RespUpload();
        if (StringUtils.isNotBlank(filepath) && StringUtils.isNotEmpty(filepath) && filepath != null) {
            cosUtil.deleteFile(cosUtil.filterUrlDomain(filepath));
        }
        try {
            cosUtil.deleteFile(cosUtil.filterUrlDomain(imgpath));
            respUpload.setSrc(filepath);
        } catch (Exception e) {
            return ResultMessage.FAILURE.result(respUpload).setMsg("删除失败");
        }
        return ResultMessage.SUCCESS.result(respUpload);
    }

}
