package work.metanet.api.sn.protocol;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ReqRemoveSerialNumber implements Serializable{
	
	private static final long serialVersionUID = 8698493709431295018L;
	
	@NotBlank(message = "SN码不能为空")
	private String snCode;

}
