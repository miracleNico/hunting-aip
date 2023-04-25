package work.metanet.api.openDevice.protocol;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class ReqOpenDeviceRemove implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	@NotBlank
    private String openDeviceId;
	
    
    

}
