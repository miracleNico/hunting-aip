package work.metanet.api.log.protocol;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor(staticName = "of")
@Accessors(chain =  true)
@NoArgsConstructor
@Data
public class ReqSaveOperLog implements Serializable{
	
	private static final long serialVersionUID = 1585035878776722846L;
	
	@NonNull
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
	private Boolean isPost;
	private String os;
	private String browser;
	private String ip;
	private String createTime;
	private Long time;
	@JsonIgnore
	private Long startTime;
	
}
