package work.metanet.vision.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import work.metanet.vision.dao.EyeMapper;
import work.metanet.vision.dao.EyesArticleExerciseRelationMapper;
import work.metanet.vision.dao.UserMapper;
import work.metanet.vision.dao.VisionExerciseMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import work.metanet.api.vision.IVisionExercise;
import work.metanet.api.vision.protocol.*;
import work.metanet.base.RespPaging;
import work.metanet.exception.DaasException;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author nicely
 * @Description 服务类：锻炼活动
 * @date 2021年05月18日10:16
 */
@DubboService
public class VisionExerciseService implements IVisionExercise {

    @Autowired
    private VisionExerciseMapper visionExerciseMapper;

    @Autowired
    private EyeMapper eyeMapper;

    @Autowired
    private EyesArticleExerciseRelationService eyesArticleExerciseRelationService;

    @Override
    public RespPaging<ReqVisionExerciseList.RespVisionExerciseList> getVisionExerciseList(ReqVisionExerciseList req) throws Exception {
        RespPaging<ReqVisionExerciseList.RespVisionExerciseList> respPaging = new RespPaging<>();
        PageHelper.startPage(req.getPageNum(), req.getPageSize());
        List<ReqVisionExerciseList.RespVisionExerciseList> visionExerciseList = visionExerciseMapper.getVisionExerciseList(BeanUtil.beanToMap(req));
        PageInfo<ReqVisionExerciseList.RespVisionExerciseList> respVisionExerciseListPageInfo = new PageInfo<>(visionExerciseList);
        BeanUtil.copyProperties(respVisionExerciseListPageInfo, respPaging);
        return respPaging;
    }

    @Override
    public List<ReqVisionExerciseList.RespVisionExerciseList> getVisionExerciseListGroupByTypeOrderByTime(ReqVisionExerciseList req) throws Exception {
        return visionExerciseMapper.getVisionExerciseListGroupByTypeOrderByTime(BeanUtil.beanToMap(req));
    }

    @Override
    public ReqVisionExerciseInfo.RespVisionExerciseInfo getVisionExerciseInfo(ReqVisionExerciseInfo req) throws Exception {
        ReqVisionExerciseInfo.RespVisionExerciseInfo visionExerciseInfo = null;
        try {
            visionExerciseInfo = visionExerciseMapper.getVisionExerciseInfo(BeanUtil.beanToMap(req));
        } catch (Exception e) {
            throw DaasException.of().setMsg("获取信息异常" + e.getMessage());
        }
        if (visionExerciseInfo == null) {
            throw DaasException.of().setMsg("数据返回为空");
        }
        return visionExerciseInfo;
    }

    @Transactional
    @Override
    public ReqSaveVisionExercise.RespSaveVisionExercise updateVisionExercise(ReqSaveVisionExercise req) throws Exception {
        ReqSaveVisionExercise.RespSaveVisionExercise respSaveVisionExercise = new ReqSaveVisionExercise.RespSaveVisionExercise();
        int updateRows;
        try {
            updateRows = visionExerciseMapper.updateVisionExercise(BeanUtil.beanToMap(req));
        } catch (Exception e) {
            throw DaasException.of().setMsg("修改异常"+e.getMessage());
        }
        if (updateRows > 0) {
            BeanUtil.copyProperties(req, respSaveVisionExercise);
        }
        return respSaveVisionExercise;
    }

    @Override
    public ReqSaveVisionExercise.RespSaveVisionExercise insertVisionExercise(ReqSaveVisionExercise req) throws Exception {
        ReqSaveVisionExercise.RespSaveVisionExercise respSaveVisionExercise = new ReqSaveVisionExercise.RespSaveVisionExercise();
        int updateRows;
        req.setExerciseId(IdUtil.fastSimpleUUID());
        try {
            updateRows = visionExerciseMapper.insertVisionExercise(BeanUtil.beanToMap(req));
        } catch (Exception e) {
            throw DaasException.of().setMsg("插入异常"+e.getMessage());
        }
        if (updateRows > 0) {
            BeanUtil.copyProperties(req, respSaveVisionExercise);
        }
        return respSaveVisionExercise;
    }

    @Override
    public List<ReqRemoveVisionExercise> removeVisionExercise(List<ReqRemoveVisionExercise> list) throws Exception {
        int updateRows = 0;
        try {
            updateRows = visionExerciseMapper.removeVisionExercise(list);
        } catch (Exception e) {
            throw DaasException.of().setMsg("删除异常：" + e.getMessage());
        }
        if (updateRows <= 0) {
            throw DaasException.of().setMsg("删除数据为空");
        }
        return list;
    }

    @Override
    public List<ReqVisionExerciseList.RespVisionExerciseList> getExerciseCount(ReqVisionExerciseList req) throws Exception {
        return visionExerciseMapper.getExerciseCount(req);
    }
}

