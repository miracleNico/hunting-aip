package work.metanet.api.device.protocol;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class ReqRemoveDevice implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	
	@NotBlank(message = "设备不能为空")
    private String deviceId;
	
    
    

}
