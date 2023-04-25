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

import work.metanet.api.channel.protocol.ReqChannelInfo.RespChannelInfo;
import work.metanet.api.channel.protocol.ReqChannelList.RespChannelList;
import work.metanet.api.channel.protocol.ReqRemoveChannel;
import work.metanet.model.Channel;

import tk.mybatis.mapper.common.Mapper;

public interface ChannelMapper extends Mapper<Channel>{
	
	Channel getChannel(Map<String, Object> map);
	
	RespChannelInfo channelInfo(Map<String, Object> map);
	
	/**
	 * @Description: 列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	List<RespChannelList> channelList(Map<String, Object> map);
	
	/**
	 * @Description: 删除渠道
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	int removeChannel(@Param("list")List<ReqRemoveChannel> list);
	
}
