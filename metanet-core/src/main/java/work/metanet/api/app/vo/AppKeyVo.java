package work.metanet.api.app.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class AppKeyVo implements Serializable{
	
	private static final long serialVersionUID = 4540553187933138837L;
	private String appKey;
	private String appSecret;

}
