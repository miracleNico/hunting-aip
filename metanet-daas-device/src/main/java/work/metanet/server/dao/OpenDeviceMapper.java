package work.metanet.server.dao;

import java.util.List;

import work.metanet.api.openDevice.protocol.ReqOpenDeviceList;
import work.metanet.api.openDevice.protocol.ReqOpenDeviceList.RespOpenDeviceList;
import work.metanet.model.OpenDevice;

import tk.mybatis.mapper.common.Mapper;

public interface OpenDeviceMapper extends Mapper<OpenDevice>{

	List<RespOpenDeviceList> deviceList(ReqOpenDeviceList req);
	
}
