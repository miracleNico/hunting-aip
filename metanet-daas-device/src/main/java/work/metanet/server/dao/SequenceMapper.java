/**  
 * @Title: OrderMapper.java
 * @Description: TODO
 * @author wanbo
 * @date 2018年3月28日
 */
package work.metanet.server.dao;

import org.apache.ibatis.annotations.Select;

public interface SequenceMapper{
	
	@Select("SELECT sequence_NEXTVAL('channel')")
	String generateChannelId();
	
	@Select("SELECT sequence_NEXTVAL('device')")
	String generateDeviceId();
	
	@Select("SELECT sequence_NEXTVAL('brand')")
	String generateBrandId();
	
	@Select("SELECT sequence_NEXTVAL('model')")
	String generateModelId();
	
	@Select("SELECT CONCAT(DATE_FORMAT(now(),'%y%m%d%h%i%s'),sequence_NEXTVAL('order'))")
	String generateOrderId();
	
	
}
