/**  
 * @Title: OrderMapper.java
 * @Description: TODO
 * @author wanbo
 * @date 2018年3月28日
 */
package work.metanet.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import work.metanet.api.sn.protocol.ReqRemoveSerialNumber;
import work.metanet.api.sn.protocol.ReqSerialNumberInfo.RespSerialNumberInfo;
import work.metanet.api.sn.protocol.ReqSerialNumberList.RespSerialNumberList;
import work.metanet.model.SerialNumber;
import work.metanet.server.vo.SerialNumberVo;

import tk.mybatis.mapper.common.Mapper;

public interface SerialNumberMapper extends Mapper<SerialNumber>{
	
	@Select("select count(1) from t_serial_number where app_id=#{appId} and status=true")
	int appSnNumber(@Param("appId")String appId);
	
	SerialNumberVo getSerialNumber(String snCode);
	
	@Select("SELECT * FROM `t_serial_number` where `status`=true and app_id=#{appId} order by max_use_number desc limit 1")
	SerialNumberVo getSerialNumberOnlyOne(@Param("appId")String appId);
	
	@Update("update t_serial_number set use_status=true,use_number=use_number+1 where sn_code=#{snCode} and use_number<max_use_number")
	Integer updateSerialNumberUseNumber(@Param("snCode")String snCode);
	
	/**
	 * @Description: 量产SN码
	 * @Author wanbo
	 * @DateTime 2019/11/21
	 */
	int addSerialNumber(@Param("list")List<SerialNumber> list);
	
	/**
	 * @Description: SN码详情
	 * @Author wanbo
	 * @DateTime 2019/11/26
	 */
	RespSerialNumberInfo serialNumberInfo(Map<String, Object> map);
	
	/**
	 * @Description: SN码列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	List<RespSerialNumberList> serialNumberList(Map<String, Object> map);
	
	/**
	 * @Description: 删除SN码
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	int removeSerialNumber(@Param("list")List<ReqRemoveSerialNumber> list);
	
}
