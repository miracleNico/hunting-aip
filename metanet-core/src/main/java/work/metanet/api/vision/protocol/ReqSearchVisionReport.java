package work.metanet.api.vision.protocol;

import java.io.Serializable;

import work.metanet.api.vision.protocol.ReqVisionReportInfo.RespVisionReportInfo;
import work.metanet.base.Paging;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@ApiModel("请求-搜索视力报告")
@EqualsAndHashCode(callSuper=true)
@Data
public class ReqSearchVisionReport extends Paging implements Serializable{
	private static final long serialVersionUID = 1544704219566273636L;
	
	@ApiModelProperty(value = "测试模式（0自测模式，1家长协助模式，2医生协助模式）", example = "1")
	private int testMode;
	
	@ApiModelProperty(value = "设备类型（0视力表灯箱、1手机、2平板、3书桌、4电视、5台灯、6其他）", example = "1")
	private int deviceType;
	
	@ApiModelProperty(value = "关键字，搜索建议或诊断", example = "卫生")
	private String keyword;
	
	@ApiModelProperty(value = "用户ID", example = "e7e6dfb0a83911eb943f00ff71c9db07")
//	@JsonIgnore
	private String userId;
	
	@ApiModelProperty(value = "拥有者ID（三方系统的用户ID）", example = "9999")
//	@JsonIgnore
	private String ownerId;
	
    @ApiModel("响应-搜索报告")
    @EqualsAndHashCode(callSuper=true)
    @Accessors(chain = true)
    @Data
    public static class RespSearchReportList extends RespVisionReportInfo implements Serializable{
		private static final long serialVersionUID = 3314095303588567669L;
    }
}
