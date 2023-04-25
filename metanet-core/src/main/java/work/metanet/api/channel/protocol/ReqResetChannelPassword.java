package work.metanet.api.channel.protocol;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ReqResetChannelPassword implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	
	@NotBlank(message = "渠道id不能为空")
	private String channelId;
    

}
