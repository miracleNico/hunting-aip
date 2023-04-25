package work.metanet.utils.token;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class JwtPayload implements Serializable{
	
	private static final long serialVersionUID = -1848448433378538070L;
	
	private String sub;
	//唯一id
	private String jti;
	//签发时间
	private Long iat;
	//过期时间
	private Long exp;

}
