package work.metanet.base;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserToken implements Serializable{

	private static final long serialVersionUID = 5829787395067976511L;
	
	private String cid;
	private String deviceId;
	private String appId;
    private String userId;
    private String userName;
    private boolean refresh = false;
    
}
