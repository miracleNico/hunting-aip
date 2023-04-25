package work.metanet.api.versionModule.protocol;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("请求-展示产品模块")
@Data
public class ReqViewAppVersionModule implements Serializable{
	
	private static final long serialVersionUID = -9092340554537792392L;
	
	@ApiModel("响应-展示产品模块")
	@Data
	public static class RespViewAppVersionModule implements Serializable{
		
		private static final long serialVersionUID = -8297490995540167594L;
		@ApiModelProperty("模块名称")
		private String moduleName;
		@ApiModelProperty("调用类型")
	    private String callType;
		@ApiModelProperty("序号")
		private Integer sort;
		@ApiModelProperty("父级id")
	    private String parentId;
		@ApiModelProperty("调用包名")
	    private String packageName;
		@ApiModelProperty("调用类名")
	    private String className;
		@ApiModelProperty("参数")
	    private String parameter;
		@ApiModelProperty("图标")
	    private String moduleIcon;
		@ApiModelProperty("内容商产品下载地址")
	    private String businessAppsUrl;
		@ApiModelProperty("内容商代码")
	    private String businessCode;
		
	}
	
}
