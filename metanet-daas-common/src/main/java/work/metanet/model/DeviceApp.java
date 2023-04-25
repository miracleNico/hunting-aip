package work.metanet.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Table(name = "t_device_app")
public class DeviceApp implements Serializable{
	
	private static final long serialVersionUID = 6332667356666900198L;
	
	@Id
	private String deviceAppId;
	private String deviceId;
	private String appId;
    private String snCode;
    private Date activateTime;
	private Date createTime;

}
