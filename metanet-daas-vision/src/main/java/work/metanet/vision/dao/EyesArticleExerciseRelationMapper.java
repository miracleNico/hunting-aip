package work.metanet.vision.dao;

import work.metanet.api.vision.protocol.ReqEyesArticleExerciseRelationInfo;
import work.metanet.api.vision.protocol.ReqRemoveEyesArticleExerciseRelation;
import work.metanet.base.RespPaging;
import org.apache.ibatis.annotations.Mapper;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author nicely
 * @Description
 * @date 2021年05月25日11:13
 */
@Repository
@Mapper
public interface EyesArticleExerciseRelationMapper {

    ReqEyesArticleExerciseRelationInfo.RespEyesArticleExerciseRelationInfo getreaeri(ReqEyesArticleExerciseRelationInfo req) throws Exception;

    RespPaging<List<ReqEyesArticleExerciseRelationInfo.RespEyesArticleExerciseRelationInfo>> getreaerl() throws Exception;

    void insertreaerl(ReqEyesArticleExerciseRelationInfo req) throws Exception;

    ReqEyesArticleExerciseRelationInfo.RespEyesArticleExerciseRelationInfo updatetreaerl(ReqEyesArticleExerciseRelationInfo req) throws Exception;

    void removetreaerl(ReqRemoveEyesArticleExerciseRelation req) throws Exception;

}
