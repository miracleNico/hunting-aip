package work.metanet.vision.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import work.metanet.vision.dao.VisionContentMapper;
import com.google.gson.Gson;
import work.metanet.api.visionContent.IVisionContentService;
import work.metanet.base.Result;
import work.metanet.base.ResultMessage;
import work.metanet.exception.DaasException;
import work.metanet.model.VisionContentBase;
import lombok.extern.slf4j.Slf4j;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import work.metanet.api.visionContent.protocol.*;
import work.metanet.base.RespPaging;
import work.metanet.constant.ConstEyesArticleType;
import work.metanet.utils.CosUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.ibatis.annotations.Param;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DubboService
@Slf4j
public class VisionContentService implements IVisionContentService {

    @Autowired
    private VisionContentMapper visionContentMapper;

    @Autowired
    private CosUtil cosUtil;

    @Override
    public List<ReqRemoveVisionContent.RespRemoveVisionContent> removeVisionContents(List<ReqRemoveVisionContent> req) throws Exception {
        List<ReqRemoveVisionContent.RespRemoveVisionContent> respRemoveVisionContents = new ArrayList<>();
        int removeRows = visionContentMapper.removeVisionContents(req);
        if (removeRows > 0) {
            for (ReqRemoveVisionContent reqRemoveVisionContent : req) {
                ReqRemoveVisionContent.RespRemoveVisionContent respRemoveVisionContent = new ReqRemoveVisionContent.RespRemoveVisionContent();
                BeanUtil.copyProperties(reqRemoveVisionContent, respRemoveVisionContent);
                respRemoveVisionContents.add(respRemoveVisionContent);
            }
        }
        return respRemoveVisionContents;
    }

    @Override
    public ReqVisionContentInfo.RespVisionContentInfo visionContentInfo(ReqVisionContentInfo req) throws Exception {
        ReqVisionContentInfo.RespVisionContentInfo resp = visionContentMapper.visionContentInfo(req);
        return resp;
    }

    @Transactional
    @Override
    public ReqUpdateVisionContent.RespUpdateVisionContent updateVisionContent(ReqUpdateVisionContent req) throws Exception {
        ReqUpdateVisionContent.RespUpdateVisionContent resp = new ReqUpdateVisionContent.RespUpdateVisionContent();
        ReqVisionContentInfo.RespVisionContentInfo respVisionContentInfo = visionContentMapper.visionContentInfo(new ReqVisionContentInfo().setContentId(req.getContentId()));
        if (respVisionContentInfo == null) {
            throw DaasException.of().setMsg("指定ID的内容不存在！");
        }
        int updateRows = visionContentMapper.updateVisionContent(req);
        if (updateRows > 0) {
            BeanUtil.copyProperties(req, resp);
        }
        return resp;
    }

    @Override
    public ReqVisionContentInfo.RespVisionContentInfo insertVisionContent(ReqVisionContentInfo req) throws Exception {
        ReqVisionContentInfo.RespVisionContentInfo resp = new ReqVisionContentInfo.RespVisionContentInfo();
        req.setContentId(IdUtil.fastSimpleUUID());
        req.setExternalLink(uploadHtml(req));
        int insertRows = visionContentMapper.insertVisionContent(req);
        if (insertRows > 0) {
            BeanUtil.copyProperties(req, resp);
        }
        return resp;
    }

    @Override
    public RespPaging<ReqVisionContentList.RespVisionContentList> visionContentList(ReqVisionContentList req) throws Exception {
        RespPaging<ReqVisionContentList.RespVisionContentList> respPaging = new RespPaging<ReqVisionContentList.RespVisionContentList>();
        PageHelper.startPage(req.getPageNum(), req.getPageSize());
        List<ReqVisionContentList.RespVisionContentList> list = visionContentMapper.visionContentList(req);
        BeanUtil.copyProperties(new PageInfo<>(list), respPaging);
        return respPaging;
    }

    @Override
    public ReqVisionContentInfo.RespVisionContentInfo ranContent(List<ReqVisionContentInfo> infos) throws Exception {
        ArrayList<String> contentIds = new ArrayList<>();
        if (infos != null) {
            for (ReqVisionContentInfo info : infos) {
                contentIds.add(info.getContentId());
            }
        }
        return getContent(contentIds);
    }

    @Override
    public List<ReqVisionContentInfo.RespVisionContentInfo> getExerciseType() throws Exception {
        return visionContentMapper.getExerciseType();
    }

    @Override
    public int getAllCount(List<String> ids) throws Exception {
        return visionContentMapper.getAllCount(ids);
    }

    private int getTotal(int type1,List<String> contentIds) throws Exception {
        return visionContentMapper.getCount(type1, contentIds);
    }

    private List<Integer> getTypeCount() throws Exception {
        return visionContentMapper.getTypeCount();
    }

    private ReqVisionContentInfo.RespVisionContentInfo getContent(List<String> contentIds) throws Exception {
        if (getAllCount(contentIds)<=0){
            return null;
        }
        ReqVisionContentInfo.RespVisionContentInfo respVisionContentInfo = null;
        List<Integer> types = getTypeCount();
        int type = (int) (Math.random() * getTypeCount().size());
        Integer type1 = types.get(type);
        int total = getTotal(type1, contentIds);
        HashMap<String, Object> map = new HashMap<>();
        if (total > 0) {
            int randomContentNum = (int) (Math.random() * total);
            map.put("type1", type1);
            map.put("num", randomContentNum);
            map.put("contentIds", contentIds);
            respVisionContentInfo = visionContentMapper.getRandomContent(map);
            return respVisionContentInfo;
        } else {
            return getContent(contentIds);
        }
    }


    private String uploadHtml(ReqVisionContentInfo req) throws Exception {
        String prefix = "<!DOCTYPE html><html lang='zh'><head><meta charset='UTF-8'><meta name='viewport' content='width=device-width, initial-scale=1.0'><meta http-equiv='X-UA-Compatible' content='ie=edge'><title></title></head><body>";
        String suffix = "</body></html>";
        String url = "";
        if (req.getExternalLink() != null && req.getExternalLink() != "") {
            url = cosUtil.filterUrlDomain(req.getExternalLink());
        }
        String content = req.getText();
        String contentId = req.getContentId();
        if (StringUtils.isEmpty(content) && StringUtils.isBlank(content)) {
            return "";
        }

//        创建本地文件
        content = prefix + content + suffix;
        String fileUrl = "";
        File folder = new File("upload");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        try {
            File file = new File("upload/" + contentId + ".html");
            String key = StrUtil.concat(true, "contentHtml/", contentId, ".html");
            PrintStream ps = new PrintStream(new FileOutputStream(file));
            ps.println(content);// 往文件里写入字符串
            //过滤u域名,将对象存储中的文件删除
            if (StringUtils.isNotBlank(req.getExternalLink()) && StringUtils.isNotEmpty(req.getExternalLink())) {
                cosUtil.deleteFile(url);
            }
            //将本地文件上传到对象存储
            fileUrl = cosUtil.upload(key, file);

            //修改数据库内容
            /*
            if (StringUtils.isNotEmpty(req.getContentId()) && StringUtils.isNotBlank(req.getContentId())) {
                ReqUpdateVisionContent eyesArticle = (ReqUpdateVisionContent) new ReqUpdateVisionContent().setContentId(req.getContentId()).setText(req.getText());
                try {
                    System.out.println(req.getText());
                    eyesArticleService.updateVisionContent(eyesArticle);
                } catch (Exception e) {
                    throw DaasException.of().setMsg("内容修改失败");
                }
            }*/
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileUrl;
    }


}
