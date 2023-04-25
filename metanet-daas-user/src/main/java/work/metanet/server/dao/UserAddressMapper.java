/**  
 * @Title: OrderMapper.java
 * @Description: TODO
 * @author wanbo
 * @date 2018年3月28日
 */
package work.metanet.server.dao;

import org.apache.ibatis.annotations.Select;

import work.metanet.model.UserAddress;

import tk.mybatis.mapper.common.Mapper;

public interface UserAddressMapper extends Mapper<UserAddress>{
	
	@Select("select * from t_user_address where user_id=#{userId} and default_status")
	UserAddress userAddressInfo(String userId);
	
}
