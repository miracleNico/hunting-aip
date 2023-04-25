package work.metanet.api.vision;

import work.metanet.api.vision.protocol.ReqEyesArticleExerciseRelationInfo;
import work.metanet.api.vision.protocol.ReqRemoveEyesArticleExerciseRelation;
import work.metanet.base.RespPaging;

import java.util.List;

/**
 * @author nicely
 * @Description
 * @date 2021年05月25日11:20
 */
public interface IEyesArticleExercise {

    ReqEyesArticleExerciseRelationInfo.RespEyesArticleExerciseRelationInfo getreaeri(ReqEyesArticleExerciseRelationInfo req) throws Exception;

    RespPaging<List<ReqEyesArticleExerciseRelationInfo.RespEyesArticleExerciseRelationInfo>> getreaerl() throws Exception;

    ReqEyesArticleExerciseRelationInfo.RespEyesArticleExerciseRelationInfo insertreaerl(ReqEyesArticleExerciseRelationInfo req) throws Exception;

    ReqEyesArticleExerciseRelationInfo.RespEyesArticleExerciseRelationInfo updatetreaerl(ReqEyesArticleExerciseRelationInfo req)throws Exception;

    void removetreaerl(ReqRemoveEyesArticleExerciseRelation req) throws Exception;

}

