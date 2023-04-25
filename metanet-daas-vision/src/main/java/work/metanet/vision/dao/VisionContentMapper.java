package work.metanet.vision.dao;

import work.metanet.api.visionContent.protocol.*;
import work.metanet.model.VisionContentBase;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.mongodb.repository.Query;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface VisionContentMapper extends Mapper<VisionContentBase>{
	
	int removeVisionContents(@Param("list") List<ReqRemoveVisionContent> list);

	List<ReqVisionContentList.RespVisionContentList> visionContentList(ReqVisionContentList req);

	ReqVisionContentInfo.RespVisionContentInfo visionContentInfo(ReqVisionContentInfo req);

	int updateVisionContent(ReqUpdateVisionContent req);

	int insertVisionContent(ReqVisionContentInfo req);

	int getCount(@Param("type1") int type1,@Param("ids") List<String> contentIds)throws Exception;

	ReqVisionContentInfo.RespVisionContentInfo getRandomContent(Map<String,Object> map)throws Exception;

	List<Integer> getTypeCount()throws Exception;

	List<ReqVisionContentInfo.RespVisionContentInfo> getExerciseType() throws Exception;

	int getAllCount(@Param("contentIds") List<String> contentIds)throws Exception;

}