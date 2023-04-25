package work.metanet.model.mongo;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description: 操作日志
 * @Author wanbo
 * @DateTime 2019/12/19
 */
@Document(collection = "t_oper_log")
@Accessors(chain =  true)
@Data
public class OperLog implements Serializable{
	
	private static final long serialVersionUID = -5489403373933485462L;
	@Id
	private String traceId;
	private String appName;
	private String appId;
	private String userId;
	private String userName;
	private String uri;
	private String header;
	private String body;
	private String param;
	private String resp;
	private String module;
	private String action;
	private String desc;
	private boolean isPost;
	private String os;
	private String browser;
	private String ip;
	private String createTime;
	private Long time;
	
}
