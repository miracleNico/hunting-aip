package work.metanet.api.channel.protocol;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ReqRemoveChannel implements Serializable{
	
	private static final long serialVersionUID = -3227451593613708414L;
	
	@NotBlank(message = "渠道不能为空")
	private String channelId;

}
