package work.metanet.api.vision;

import work.metanet.api.vision.protocol.*;
import work.metanet.base.RespPaging;

import java.util.List;

/**
 * @author EdisonFeng
 * @Description 视力计划，用于rpc服务
 * @DateTime 2021年5月15日
 * Copyright(c) 2021. All Rights Reserved
 */

public interface IVisionPlan {

    /**
     * 查询计划列表
     * @param req
     * @return
     * @throws Exception
     */
    RespPaging<ReqVisionPlanList.RespVisionPlanList> getVisionPlanList(ReqVisionPlanList req) throws Exception;

    /**
     * 获取计划详情
     * @param req
     * @return
     * @throws Exception
     */
    ReqVisionPlanInfo.RespVisionPlanInfo getVisionPlanInfo(ReqVisionPlanInfo req) throws Exception;

    /**
     * 保存计划
     * @param req
     * @return
     * @throws Exception
     */
    ReqSaveVisionPlan.RespSaveVisionPlan saveVisionPlan(ReqSaveVisionPlan req) throws Exception;

    /**
     * 新增计划
     * @param req
     * @return
     * @throws Exception
     */
    ReqSaveVisionPlan.RespSaveVisionPlan insertVisionPlan(ReqInsertVisionPlan req) throws Exception;

    /**
     * 删除计划
     * @param req
     * @return
     * @throws Exception
     */
    List<ReqRemoveVisionPlan> removeVisionPlan(List<ReqRemoveVisionPlan> req) throws Exception;
}
