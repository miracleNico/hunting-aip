package work.metanet.api.admin.protocol;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@Data
public class ReqSaveAdmin implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	private String adminId;
	private String username;
	private Boolean enableStatus;
	private String remark;
	private List<String> roleIdList;
	
}
