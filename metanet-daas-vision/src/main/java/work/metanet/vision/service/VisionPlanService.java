package work.metanet.vision.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import work.metanet.vision.dao.VisionPlanMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import work.metanet.api.vision.IVisionPlan;
import work.metanet.api.vision.protocol.*;
import work.metanet.base.RespPaging;
import work.metanet.exception.DaasException;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author nicely
 * @Description
 * @date 2021年05月18日14:43
 */
@DubboService
public class VisionPlanService implements IVisionPlan {

    @Autowired
    private VisionPlanMapper visionPlanMapper;

    @Override
    public RespPaging<ReqVisionPlanList.RespVisionPlanList> getVisionPlanList(ReqVisionPlanList req) throws Exception {
        RespPaging<ReqVisionPlanList.RespVisionPlanList> respPaging = new RespPaging<>();
        PageHelper.startPage(req.getPageNum(), req.getPageSize());
        if (req.getEndDate() != null){
            req.setEndDate(new Date(req.getEndDate().getTime()+1000*60*60*24-1));
        }

        List<ReqVisionPlanList.RespVisionPlanList> visionPlanList =  visionPlanMapper.getVisionPlanList(BeanUtil.beanToMap(req));
        PageInfo<ReqVisionPlanList.RespVisionPlanList> respVisionPlanListPageInfo = new PageInfo<>(visionPlanList);
        respPaging.setList(visionPlanList);
        BeanUtil.copyProperties(visionPlanList, respPaging);
        return respPaging;
    }

    @Override
    public ReqVisionPlanInfo.RespVisionPlanInfo getVisionPlanInfo(ReqVisionPlanInfo req) throws Exception {
        ReqVisionPlanInfo.RespVisionPlanInfo visionPlanInfo = null;
        try {
            visionPlanInfo = visionPlanMapper.getVisionPlanInfo(BeanUtil.beanToMap(req));
        } catch (Exception e) {
            throw DaasException.of().setMsg("获取计划信息异常" + e.getMessage());
        }
        if (visionPlanInfo == null) {
            throw DaasException.of().setMsg("数据为空");
        }

        return visionPlanInfo;
    }

    @Transactional
    @Override
    public ReqSaveVisionPlan.RespSaveVisionPlan saveVisionPlan(ReqSaveVisionPlan req) throws Exception {
        ReqSaveVisionPlan.RespSaveVisionPlan respSaveVisionPlan = new ReqSaveVisionPlan.RespSaveVisionPlan();
        ReqVisionPlanInfo.RespVisionPlanInfo visionPlanInfo = new ReqVisionPlanInfo.RespVisionPlanInfo();
        int updateRows = 0;
        if (StringUtils.isBlank(req.getUserId())|| StringUtils.isEmpty(req.getUserId())){
            ReqVisionPlanInfo reqVisionPlanInfo = new ReqVisionPlanInfo().setPlanId(req.getPlanId());
            visionPlanInfo = visionPlanMapper.getVisionPlanInfo(BeanUtil.beanToMap(reqVisionPlanInfo));
        }
        try {
            updateRows = visionPlanMapper.saveVisionPlan(BeanUtil.beanToMap(req));
        } catch (Exception e) {
            throw DaasException.of().setMsg("修改计划异常" + e.getMessage());
        }
        if (updateRows <= 0) {
            throw DaasException.of().setMsg("修改数据为空");
        } else {
            BeanUtil.copyProperties(req, respSaveVisionPlan);
            respSaveVisionPlan.setUserId(visionPlanInfo.getUserId());
        }
        return respSaveVisionPlan;
    }

    @Override
    public ReqSaveVisionPlan.RespSaveVisionPlan insertVisionPlan(ReqInsertVisionPlan req) throws Exception {
        ReqSaveVisionPlan.RespSaveVisionPlan respSaveVisionPlan = new ReqSaveVisionPlan.RespSaveVisionPlan();
        int insertRows = 0;
        try {
            req.setPlanId(IdUtil.fastSimpleUUID());
            insertRows = visionPlanMapper.insertVisionPlan(BeanUtil.beanToMap(req));
        } catch (Exception e) {
            throw DaasException.of().setMsg("新增计划异常" + e.getMessage());
        }
        if (insertRows <= 0) {
            throw DaasException.of().setMsg("新增数据为空");
        } else {
            BeanUtil.copyProperties(req, respSaveVisionPlan);
        }
        return respSaveVisionPlan;
    }

    @Override
    public List<ReqRemoveVisionPlan> removeVisionPlan(List<ReqRemoveVisionPlan> list) throws Exception {
        int removeRows = 0;
        try {
            removeRows = visionPlanMapper.removeVisionPlan(list);
        } catch (Exception e) {
            throw DaasException.of().setMsg("删除计划异常" + e.getMessage());
        }
        if (removeRows <= 0) {
            throw DaasException.of().setMsg("删除数据为空");
        }
        return list;
    }
}

