package work.metanet.api.device.protocol;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class ReqEnableDevice implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	
	@NotBlank(message = "设备id不能为空")
    private String deviceId;
	@NotNull(message = "启用状态不能为空")
    private Boolean enableStatus;
    

}
