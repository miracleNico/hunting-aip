
package work.metanet.vision.service;

import java.util.*;

import work.metanet.api.vision.protocol.*;
import work.metanet.model.User;
import work.metanet.server.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import work.metanet.vision.dao.VisionReportMapper;
import work.metanet.vision.dao.VisionTestMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import work.metanet.api.vision.IVisionReport;
import work.metanet.api.vision.protocol.ReqRecommendVisionReport.RespRecommendReportList;
import work.metanet.api.vision.protocol.ReqSaveVisionReport.RespSaveVisionReport;
import work.metanet.api.vision.protocol.ReqSearchVisionReport.RespSearchReportList;
import work.metanet.api.vision.protocol.ReqVisionReportInfo.RespVisionReportInfo;
import work.metanet.api.vision.protocol.ReqVisionReportList.RespVisionReportList;
import work.metanet.api.vision.protocol.ReqVisionTestInfo.RespVisionTestInfo;
import work.metanet.base.RespPaging;
import work.metanet.exception.DaasException;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author EdisonFeng
 * @Description 服务：视力报告
 * @DateTime 2021年4月21日
 * Copyright(c) 2021. All Rights Reserved
 */
@DubboService
//@GlobalTransactional(rollbackFor = Exception.class)
public class VisionReportService implements IVisionReport {
    @Autowired
    private VisionReportMapper visionReportMapper;
    @Autowired
    private VisionTestMapper visionTestMapper;

    @Override
    public RespPaging<RespVisionReportList> getVisionReportList(ReqVisionReportList req) throws Exception {
        RespPaging<RespVisionReportList> respPaging = new RespPaging<RespVisionReportList>();
        PageHelper.startPage(req.getPageNum(), req.getPageSize());

        List<RespVisionReportList> list;

        try {
            list = visionReportMapper.getVisionReportList(BeanUtil.beanToMap(req));
            BeanUtil.copyProperties(new PageInfo<RespVisionReportList>(list), respPaging);
        } catch (Exception e) {
            throw DaasException.of().setMsg("获取视力报告异常:" + e.getMessage());
        }

        return respPaging;
    }

    @Override
    public List<RespVisionReportList> getTwelveVisionReport(ReqSearchVisionReport req) throws Exception {
        Map<String, Object> map = BeanUtil.beanToMap(req);
        List<RespVisionReportList> resp = new ArrayList<>();
        map.put("UserId",req.getUserId());
        map.put("type", 1);
        List<RespVisionReportList> right = visionReportMapper.getTwelveVisionReport(map);
        sort(right);
        resp.addAll(right);
        map.put("type", 0);
        List<RespVisionReportList> left = visionReportMapper.getTwelveVisionReport(map);
        sort(left);
        resp.addAll(left);
        return resp;
    }

    private void sort(List<RespVisionReportList> req) {
        Collections.sort(req, (o1, o2) -> {
            int flag = 0;
            if (o1.getCreateTime().getTime() > o2.getCreateTime().getTime()) {
                flag = 1;
            } else {
                flag = -1;
            }
            return flag;
        });
    }

    @Override
    public RespVisionReportInfo getVisionReportInfo(ReqVisionReportInfo req) throws Exception {
        //获取视力报告
        RespVisionReportInfo resp = null;
        try {
            resp = visionReportMapper.getVisionReportInfo(BeanUtil.beanToMap(req));
        } catch (Exception e) {
            throw DaasException.of().setMsg("获取视力报告异常:" + e.getMessage());
        }
        if (resp == null) {
            resp = new RespVisionReportInfo();
        }

        return resp;
    }

    @Transactional
    @Override
    public RespSaveVisionReport saveVisionReport(ReqSaveVisionReport req) throws Exception {
        ReqSaveVisionReport reqSaveVisionReport = new ReqSaveVisionReport();
        RespSaveVisionReport respSaveVisionReport = new RespSaveVisionReport();
//		RespVisionReportInfo respVisionReportInfo = null;

        String visionReportID = req.getReportId();
//		if(StringUtils.isNotBlank(visionReportID)) {
//			//获取用户的眼睛信息
//			reqSaveVisionReport.setReportId(visionReportID);
//
//			Map<String, Object> map = BeanUtil.beanToMap(reqSaveVisionReport);
//			respVisionReportInfo = visionReportMapper.getVisionReportInfo(map);
//		}
//		String newVisionReportID = null;
//		if(BeanUtil.isNotEmpty(respVisionReportInfo)) {
//			newVisionReportID = respVisionReportInfo.getReportId();
//		}

        if (StringUtils.isNotBlank(visionReportID)) {
            BeanUtil.copyProperties(req, reqSaveVisionReport);
            reqSaveVisionReport.setReportId(visionReportID);
            //修改操作
            if (visionReportMapper.updateVisionReport(reqSaveVisionReport) <= 0) {
                throw DaasException.of().setMsg("修改失败");
            } else {
                BeanUtil.copyProperties(reqSaveVisionReport, respSaveVisionReport);
            }
        } else {
            BeanUtil.copyProperties(req, reqSaveVisionReport);
            String testId = reqSaveVisionReport.getTestId();
            if (StringUtils.isNotBlank(testId)) {
                ReqVisionTestInfo reqVisionTestInfo = new ReqVisionTestInfo();
                reqVisionTestInfo.setTestId(testId);
                RespVisionTestInfo respVisionTestInfo = visionTestMapper.getVisionTestInfo(BeanUtil.beanToMap(reqVisionTestInfo));
                if (BeanUtil.isEmpty(respVisionTestInfo) || StringUtils.isBlank(respVisionTestInfo.getTestId())) {
                    throw DaasException.of().setMsg("测试活动ID不存在：" + testId);
                }
            }

            //新增操作
            reqSaveVisionReport.setReportId(IdUtil.fastSimpleUUID());

            if (visionReportMapper.insertVisionReport(reqSaveVisionReport) <= 0) {
                throw DaasException.of().setMsg("新增失败");
            } else {
                BeanUtil.copyProperties(reqSaveVisionReport, respSaveVisionReport);
            }
        }
        return respSaveVisionReport;
    }

    @Override
    public String removeVisionReport(List<ReqRemoveVisionReport> visionReports) throws Exception {
        String reportId;
        try {
            reportId = visionReportMapper.removeVisionReport(visionReports);
        } catch (Exception e) {
            throw DaasException.of().setMsg("删除视力报告异常:" + e.getMessage());
        }
        return reportId;
    }

    @Override
    public RespPaging<RespRecommendReportList> recommendVisionReport(ReqRecommendVisionReport req) throws Exception {
        RespPaging<RespRecommendReportList> respPaging = new RespPaging<RespRecommendReportList>();
        PageHelper.startPage(req.getPageNum(), req.getPageSize());
        List<RespRecommendReportList> list = visionReportMapper.recommendVisionReport(req);
        BeanUtil.copyProperties(new PageInfo<RespRecommendReportList>(list), respPaging);
        return respPaging;
    }

    @Override
    public RespPaging<RespSearchReportList> searchVisionReport(ReqSearchVisionReport req) throws Exception {
        RespPaging<RespSearchReportList> respPaging = new RespPaging<RespSearchReportList>();
        PageHelper.startPage(req.getPageNum(), req.getPageSize());
        List<RespSearchReportList> list = visionReportMapper.searchVisionReport(req);
        BeanUtil.copyProperties(new PageInfo<RespSearchReportList>(list), respPaging);

        return respPaging;
    }

    @Override
    public List<ReqVisionAVG.RespVisionAVG> getVisionAVG(ReqVisionAVG req) throws Exception {
        List<ReqVisionAVG.RespVisionAVG> visionAVG = visionReportMapper.getVisionAVG(req);
        if (visionAVG == null) {
            throw DaasException.of().setMsg("获取平均视力异常");
        }
        return visionAVG;
    }

    @Override
    public RespSaveVisionReport insertVisionReport(ReqSaveVisionReport req) throws Exception {
        ReqSaveVisionReport reqNew = new ReqSaveVisionReport();
        RespSaveVisionReport resp = new RespSaveVisionReport();
        try {
            //获取视力报告
            String reportId = req.getReportId();

            BeanUtil.copyProperties(req, reqNew);
            //新增操作
            if (reportId == null || reportId.isEmpty()) {
                reqNew.setReportId(IdUtil.fastSimpleUUID());
            }
            //使用遗漏很多字段：INSERT INTO t_vision_report ( test_id,proposal,report_id,create_time,update_time,remark ) VALUES( ?,?,?,?,?,? )
//			visionReportMapper.insertSelective(visionReport);
            if (visionReportMapper.insertVisionReport(reqNew) > 0) {
                BeanUtil.copyProperties(reqNew, resp);
            }
        } catch (Exception e) {
            throw DaasException.of().setMsg("保存视力报告异常:" + e.getMessage());
        }

        return resp;
    }

}
