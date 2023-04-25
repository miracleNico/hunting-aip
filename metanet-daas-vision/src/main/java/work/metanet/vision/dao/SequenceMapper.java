package work.metanet.vision.dao;

import org.apache.ibatis.annotations.Select;
/**
 * @author Edison F.
 * @Description 产生序列号
 * @DateTime 2021/04/20
 */
public interface SequenceMapper{
	
	@Select("SELECT sequence_NEXTVAL('eye')")
	String generateEyeId();
	
	@Select("SELECT sequence_NEXTVAL('vision_test')")
	String generateTestId();
	
	@Select("SELECT sequence_NEXTVAL('vision_report')")
	String generateReportId();

}
