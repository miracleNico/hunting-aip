/**  
 * @Title: OrderMfeedbacker.java
 * @Description: TODO
 * @author wanbo
 * @date 2018年3月28日
 */
package work.metanet.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import work.metanet.model.StatisticalAppActive;
import work.metanet.server.vo.StatisticalVo;

import tk.mybatis.mapper.common.Mapper;

public interface StatisticalAppActiveMapper extends Mapper<StatisticalAppActive>{
	
	List<StatisticalVo> appActiveUserNumber(@Param("channelId")String channelId);
	
	Integer yesterdayActiveUserTotal(@Param("channelId")String channelId);
	
}
