package work.metanet.api.model.protocol;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReqRemoveModel implements Serializable{
	
	private static final long serialVersionUID = -8542855042034048825L;
	private String modelId;
	
}
