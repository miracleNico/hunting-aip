package work.metanet.api.version.protocol;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
@Data
public class ReqValidAppVersionList extends ReqAppVersionList implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	
	//@NotBlank(message = "产品id不能为空")
	private String app;
    
	@Override
	public ReqAppVersionList setApp(String app) {
		this.app = app;
		return super.setApp(app);
	}

}
