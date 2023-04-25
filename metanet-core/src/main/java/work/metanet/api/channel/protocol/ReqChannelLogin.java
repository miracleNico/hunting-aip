package work.metanet.api.channel.protocol;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ReqChannelLogin implements Serializable{
	
	private static final long serialVersionUID = -4591673209299398390L;
	
	@NotBlank(message = "用户名不能为空")
	private String username;
	@NotBlank(message = "密码不能为空")
	private String password;
	
	
	@Data
	public static class RespChannelLogin implements Serializable{
		private static final long serialVersionUID = 9038388896107751731L;
		private String channelId;
		private String username;
		private String password;
		private String nickName;
		private String headUrl;
		private String jsonMenus;
		private String adminPermissionApis;
		private String adminPermissionTags;
	}
	
}
