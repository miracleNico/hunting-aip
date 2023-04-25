package work.metanet.api.app.protocol;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

import work.metanet.api.app.vo.AppEarningsSettingVo;

import lombok.Data;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@Data
public class ReqSaveApp implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	private String appId;
	@NotBlank(message = "渠道不能为空")
	private String channelId;
	@NotBlank(message = "产品名称不能为空")
	private String appName;
	@NotBlank(message = "产品类型不能为空")
	private String appType;
	@NotBlank(message = "包名不能为空")
	private String packageName;
	@NotBlank(message = "认证方式不能为空")
	private String authType;
	private Boolean enableSn;
	private String instruction;
    private String remark;
    private List<AppEarningsSettingVo> appEarningsSettings;

}
