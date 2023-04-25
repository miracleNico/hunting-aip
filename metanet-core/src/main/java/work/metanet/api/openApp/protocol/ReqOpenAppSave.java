package work.metanet.api.openApp.protocol;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class ReqOpenAppSave implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	private String appId;
	@NotBlank
	private String appName;
	@NotBlank
	private String channelId;
	@NotNull
	private Integer signDeviceNumber;
	private String remark;

}
