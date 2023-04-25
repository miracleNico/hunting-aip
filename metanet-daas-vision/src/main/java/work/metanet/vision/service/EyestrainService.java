
package work.metanet.vision.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import cn.hutool.core.bean.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import work.metanet.vision.dao.EyestrainMapper;
import work.metanet.vision.dao.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import work.metanet.api.user.protocol.ReqUserInfo;
import work.metanet.api.vision.IEyestrain;
import work.metanet.api.vision.protocol.ReqEyestrainInfo;
import work.metanet.api.vision.protocol.ReqEyestrainList;
import work.metanet.api.vision.protocol.ReqSaveEyestrain;
import work.metanet.api.vision.protocol.ReqSaveEyestrain.RespSaveEyestrain;
import work.metanet.api.vision.protocol.ReqRemoveEyestrain;
import work.metanet.api.vision.protocol.ReqEyestrainInfo.RespEyestrainInfo;
import work.metanet.api.vision.protocol.ReqEyestrainList.RespEyestrainList;
import work.metanet.base.RespPaging;
import work.metanet.exception.DaasException;
import work.metanet.server.vo.UserVo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;

/**
 * @author EdisonFeng
 * @Description 服务类：用眼活动
 * @DateTime 2021年5月17日
 * Copyright(c) 2021. All Rights Reserved
 */
@DubboService
public class EyestrainService implements IEyestrain {
    private static Logger log = Logger.getLogger("EyestrainService");

    @Autowired
    private EyestrainMapper eyestrainMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 获取眼部活动列表
     * @param req
     * @return
     * @throws Exception
     */
    @Override
    public RespPaging<RespEyestrainList> getEyestrainList(ReqEyestrainList req) throws Exception {
        RespPaging<RespEyestrainList> respPaging = new RespPaging<RespEyestrainList>();
        PageHelper.startPage(req.getPageNum(), req.getPageSize());
        List<RespEyestrainList> list = eyestrainMapper.getEyestrainList(BeanUtil.beanToMap(req));
        BeanUtil.copyProperties(new PageInfo<RespEyestrainList>(list), respPaging);
        System.out.println(list);
        return respPaging;
    }

    @Override
    public List<RespEyestrainList> getEyestrainListOrderByUseTypeAndGroupByTime(ReqEyestrainList req) throws Exception {
        return eyestrainMapper.getEyestrainListOrderByUseTypeAndGroupByTime(BeanUtil.beanToMap(req));
    }


    /**
     * 获取眼部活动信息
     * @param req
     * @return
     * @throws Exception
     */
    @Override
    public RespEyestrainInfo getEyestrainInfo(ReqEyestrainInfo req) throws Exception {
        //获取信息
        RespEyestrainInfo resp;
        try {
            resp = eyestrainMapper.getEyestrainInfo(BeanUtil.beanToMap(req));
        } catch (Exception e) {
            throw DaasException.of().setMsg("获取信息异常:" + e.getMessage());
        }
        if (resp == null) {
            throw DaasException.of().setMsg("查询返回为空");
        }
        return resp;
    }

    /**
     * 更新或者新增
     * @param req
     * @return
     * @throws Exception
     */
    @Transactional
    @Override
    public RespSaveEyestrain updateEyestrain(ReqSaveEyestrain req) throws Exception {
        RespSaveEyestrain respSaveEyestrain = new RespSaveEyestrain();
        int i = eyestrainMapper.updateEyestrain(req);
        if (i<=0) {
            throw DaasException.of().setMsg("修改失败异常");
        }
        return respSaveEyestrain;
    }

    /**
     * 删除眼睛活动
     * @param eyestrains
     * @return
     * @throws Exception
     */
    @Override
    public String removeEyestrain(List<ReqRemoveEyestrain> eyestrains) throws Exception {
        try {
            eyestrainMapper.removeEyestrain(eyestrains);
        } catch (Exception e) {
            throw DaasException.of().setMsg("删除信息异常:" + e.getMessage());
        }

        return "删除成功";
    }

    /**
     * 根据User获取眼部活动信息
     * @param userId
     * @return
     */
    @Override
    public List<RespEyestrainInfo> eyestrainsInfoByUser(String userId) {
        return eyestrainMapper.eyestrainsInfoByUser(userId);
    }

    /**
     * 获取用户眼部活动信息
     * @param req
     * @return
     */
    @Override
    public List<RespEyestrainInfo> getUserEyestrainInfo(ReqEyestrainInfo req) {
        return eyestrainMapper.getUserEyestrainInfo(req);
    }

    /**
     * 新增用眼活动信息
     * @param req
     * @return
     * @throws Exception
     */
    @Override
    public RespSaveEyestrain insertEyestrain(ReqSaveEyestrain req) throws Exception {
        ReqSaveEyestrain reqNew = new ReqSaveEyestrain();
        RespSaveEyestrain resp = new RespSaveEyestrain();
        try {
            //ID
            String eyestrainId = req.getEyestrainId();

            BeanUtil.copyProperties(req, reqNew);
            //新增操作
            if (eyestrainId == null || eyestrainId.isEmpty()) {
                reqNew.setEyestrainId(IdUtil.fastSimpleUUID());
            }
            if (eyestrainMapper.insertEyestrain(reqNew) > 0) {
                BeanUtil.copyProperties(reqNew, resp);
            }
        } catch (Exception e) {
            throw DaasException.of().setMsg("保存信息异常:" + e.getMessage());
        }

        return resp;
    }
}
