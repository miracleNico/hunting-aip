package work.metanet.vision.dao;

import work.metanet.api.vision.protocol.ReqRemoveVisionPlan;
import work.metanet.api.vision.protocol.ReqSaveVisionPlan;
import work.metanet.api.vision.protocol.ReqVisionPlanInfo;
import work.metanet.api.vision.protocol.ReqVisionPlanList;
import work.metanet.base.RespPaging;
import work.metanet.model.VisionPlan;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author nicely
 * @Description
 * @date 2021年05月18日14:43
 */
public interface VisionPlanMapper extends Mapper<VisionPlan> {

    /**
     * 查询计划列表
     * @param req
     * @return
     * @throws Exception
     */
    List<ReqVisionPlanList.RespVisionPlanList> getVisionPlanList(Map<String,Object> req) throws Exception;

    /**
     * 获取计划详情
     * @param req
     * @return
     * @throws Exception
     */
    ReqVisionPlanInfo.RespVisionPlanInfo getVisionPlanInfo(Map<String,Object> req) throws Exception;

    /**
     * 保存计划
     * @param req
     * @return
     * @throws Exception
     */
    int saveVisionPlan(Map<String,Object> req) throws Exception;

    /**
     * 新增计划
     * @param req
     * @return
     * @throws Exception
     */
    int insertVisionPlan(Map<String,Object> req) throws Exception;

    /**
     * 删除计划
     * @param list
     * @return
     * @throws Exception
     */
    int removeVisionPlan(List<ReqRemoveVisionPlan> list) throws Exception;
}
