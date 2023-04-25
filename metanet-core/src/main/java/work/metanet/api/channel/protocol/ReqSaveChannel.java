package work.metanet.api.channel.protocol;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@Data
public class ReqSaveChannel implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	
	private String channelId;
	@NotBlank(message = "渠道名称不能为空")
	private String channelName;
	private String remark;
	private String username;
	private List<String> roleIdList;

}
