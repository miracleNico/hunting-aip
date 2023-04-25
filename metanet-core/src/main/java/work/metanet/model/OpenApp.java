package work.metanet.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Table(name = "t_open_app")
public class OpenApp implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	private String appId;
	private String appName;
	private String appKey;
	private String channelId;
	private Integer signDeviceNumber;
	private String remark;
	private Boolean status;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(updatable = false)
	private Date createTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(updatable = false)
	private Date updateTime;

}
