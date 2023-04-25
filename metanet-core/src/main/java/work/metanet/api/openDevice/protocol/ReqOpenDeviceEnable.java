package work.metanet.api.openDevice.protocol;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class ReqOpenDeviceEnable implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@NotBlank
    private String openDeviceId;
	@NotNull
	private Boolean enable;
	private String remark;

}
