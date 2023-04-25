package work.metanet.api.version.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class AppVersionVo implements Serializable{
	
	private static final long serialVersionUID = -4640117966897541098L;
	private String versionId;
    private String appId;
	private String versionCode;
    private String versionName;

}
