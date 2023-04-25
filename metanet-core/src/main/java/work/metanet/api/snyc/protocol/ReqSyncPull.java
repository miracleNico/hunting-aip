package work.metanet.api.snyc.protocol;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("请求-同步拉取")
@Data
public class ReqSyncPull implements Serializable{
	
	private static final long serialVersionUID = 204554491311794312L;
	
	@ApiModelProperty(value = "同步模块-LEARN_DIR/TAKE_READ/TAKE_READ_DETAIL", required = true)
	@Pattern(regexp = "^(LEARN_DIR|TAKE_READ|TAKE_READ_DETAIL)$", message = "同步模块格式错误")
	private String model;
	
}
