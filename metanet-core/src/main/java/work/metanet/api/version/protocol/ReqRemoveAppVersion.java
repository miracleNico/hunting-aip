package work.metanet.api.version.protocol;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ReqRemoveAppVersion implements Serializable{
	
	private static final long serialVersionUID = -8818141974109282647L;
	
	@NotBlank(message = "版本id不能为空")
	private String versionId;

}
