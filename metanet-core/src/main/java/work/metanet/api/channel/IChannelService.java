package work.metanet.api.channel;

import java.util.List;

import work.metanet.api.channel.protocol.ReqChannelInfo;
import work.metanet.api.channel.protocol.ReqChannelInfo.RespChannelInfo;
import work.metanet.api.channel.protocol.ReqChannelList;
import work.metanet.api.channel.protocol.ReqChannelList.RespChannelList;
import work.metanet.api.channel.protocol.ReqChannelLogin;
import work.metanet.api.channel.protocol.ReqChannelLogin.RespChannelLogin;
import work.metanet.api.channel.protocol.ReqRemoveChannel;
import work.metanet.api.channel.protocol.ReqSaveChannel;
import work.metanet.api.channel.protocol.ReqUpdChannelPassword;
import work.metanet.base.RespPaging;

public interface IChannelService {
	
	/**
	 * @Description: 重置渠道密码
	 * @Author wanbo
	 * @DateTime 2020/05/12
	 */
	void resetChannelPassword(String channelId) throws Exception;
	
	RespChannelLogin login(ReqChannelLogin req) throws Exception;
	
	void updChannelPassword(String channelId, ReqUpdChannelPassword req) throws Exception;
	
	String cacheSession(String channelId) throws Exception;
	
	void cacheSession(String channelId, String sessionId) throws Exception;

	/**
	 * @Description: 渠道列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespPaging<RespChannelList> channelList(ReqChannelList req) throws Exception;
	
	/**
	 * @Description: 渠道详情
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespChannelInfo channelInfo(ReqChannelInfo req) throws Exception;
	
	/**
	 * @Description: 保存渠道
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void saveChannel(ReqSaveChannel req) throws Exception;
	
	/**
	 * @Description: 删除渠道
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void removeChannel(List<ReqRemoveChannel> req) throws Exception;
	
	
}
