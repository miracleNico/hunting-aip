package work.metanet.api.app.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class AppVo implements Serializable{
	
	private static final long serialVersionUID = 614166941711263368L;
	private String appId;
    private String appName;
    private String packageName;
    private String channelId;

}
