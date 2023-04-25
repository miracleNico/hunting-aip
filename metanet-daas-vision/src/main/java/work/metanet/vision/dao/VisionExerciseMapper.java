package work.metanet.vision.dao;

import work.metanet.api.vision.protocol.ReqRemoveVisionExercise;
import work.metanet.api.vision.protocol.ReqVisionExerciseInfo;
import work.metanet.api.vision.protocol.ReqVisionExerciseList;
import work.metanet.model.VisionExercise;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Description mapper映射类 视力锻炼
 * @author nicely
 * @date 2021年05月18日9:18
 */
public interface VisionExerciseMapper extends Mapper<VisionExercise> {
    /**
     * @Description 获取锻炼活动列表
     * @param req
     * @return 锻炼活动列表
     * @throws Exception
     * @author nicely
     */
    List<ReqVisionExerciseList.RespVisionExerciseList> getVisionExerciseList(Map<String,Object> req) throws Exception;

    /**
     * @Description 根据类型分组根据时间排序获取锻炼活动列表
     * @param req
     * @return 锻炼活动列表
     * @throws Exception
     * @author nicely
     */
    List<ReqVisionExerciseList.RespVisionExerciseList> getVisionExerciseListGroupByTypeOrderByTime(Map<String,Object> req) throws Exception;

    /**
     * 获取锻炼活动信息
     * @param req
     * @return
     * @throws Exception
     */
    ReqVisionExerciseInfo.RespVisionExerciseInfo getVisionExerciseInfo(Map<String,Object> req)throws Exception;

    /**
     * 保存锻炼活动
     * @param req
     * @return
     * @throws Exception
     */
    int updateVisionExercise(Map<String,Object>req)throws Exception;

    /**
     * 增加锻炼活动信息
     * @param req
     * @return
     * @throws Exception
     */
    int insertVisionExercise(Map<String,Object> req)throws Exception;

    /**
     * 删除锻炼活动信息
     * @param list
     * @return
     * @throws Exception
     */
    int removeVisionExercise(List<ReqRemoveVisionExercise> list)throws Exception;


    /**
     * 获取过去七天每日锻炼次数
     * @param req
     * @return
     * @throws Exception
     */
    List<ReqVisionExerciseList.RespVisionExerciseList> getExerciseCount(ReqVisionExerciseList req)throws Exception;

}
