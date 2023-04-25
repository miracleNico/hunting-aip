package work.metanet.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * t_user_login
 * @author 
 */
@Accessors(chain = true)
@Data
@Table(name = "t_user_login")
public class UserLogin implements Serializable {
	@Id
    private String userLoginId;
    private String userId;
    private String deviceId;
    private String versionId;
    private Date createTime;
    private static final long serialVersionUID = -5177084382502444761L;
}