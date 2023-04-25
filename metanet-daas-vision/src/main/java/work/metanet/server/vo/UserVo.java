package work.metanet.server.vo;

import work.metanet.model.User;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
@Data
public class UserVo extends User {
	
	private static final long serialVersionUID = -5983277593847087041L;

}
