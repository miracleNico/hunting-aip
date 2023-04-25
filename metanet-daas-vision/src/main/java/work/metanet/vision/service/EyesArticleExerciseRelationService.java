package work.metanet.vision.service;

import cn.hutool.core.util.IdUtil;
import work.metanet.vision.dao.EyesArticleExerciseRelationMapper;
import work.metanet.api.vision.IEyesArticleExercise;
import work.metanet.api.vision.protocol.ReqEyesArticleExerciseRelationInfo;
import work.metanet.api.vision.protocol.ReqRemoveEyesArticleExerciseRelation;
import work.metanet.base.RespPaging;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author nicely
 * @Description
 * @date 2021年05月25日11:19
 */
@DubboService
public class EyesArticleExerciseRelationService implements IEyesArticleExercise {

    @Autowired
    private EyesArticleExerciseRelationMapper eyesArticleExerciseRelationMapper;

    @Override
    public ReqEyesArticleExerciseRelationInfo.RespEyesArticleExerciseRelationInfo getreaeri(ReqEyesArticleExerciseRelationInfo req) throws Exception {
        return eyesArticleExerciseRelationMapper.getreaeri(req);
    }

    @Override
    public RespPaging<List<ReqEyesArticleExerciseRelationInfo.RespEyesArticleExerciseRelationInfo>> getreaerl() throws Exception {
        return eyesArticleExerciseRelationMapper.getreaerl();
    }

    @Override
    public ReqEyesArticleExerciseRelationInfo.RespEyesArticleExerciseRelationInfo insertreaerl(ReqEyesArticleExerciseRelationInfo req) throws Exception {
        String articleExerciseId = IdUtil.fastSimpleUUID();
        req.setArticleExerciseId(articleExerciseId);
        eyesArticleExerciseRelationMapper.insertreaerl(req);
        ReqEyesArticleExerciseRelationInfo.RespEyesArticleExerciseRelationInfo getreaeri = getreaeri(req);
        return getreaeri;
    }

    @Override
    public ReqEyesArticleExerciseRelationInfo.RespEyesArticleExerciseRelationInfo updatetreaerl(ReqEyesArticleExerciseRelationInfo req) throws Exception {
        return eyesArticleExerciseRelationMapper.updatetreaerl(req);
    }

    @Override
    public void removetreaerl(ReqRemoveEyesArticleExerciseRelation req) throws Exception {
        eyesArticleExerciseRelationMapper.removetreaerl(req);
    }
}

