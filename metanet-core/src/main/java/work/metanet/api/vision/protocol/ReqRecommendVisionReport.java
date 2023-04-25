package work.metanet.api.vision.protocol;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import work.metanet.api.vision.protocol.ReqVisionReportInfo.RespVisionReportInfo;
import work.metanet.base.Paging;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@ApiModel("请求-推荐视力报告")
@EqualsAndHashCode(callSuper=true)
@Data
public class ReqRecommendVisionReport extends Paging implements Serializable{

	private static final long serialVersionUID = -6869357901083372273L;
	
	@ApiModelProperty(value = "测试模式（0自测模式，1家长协助模式，2医生协助模式）", required = true, example = "0")
	private int testMode;
	
	@ApiModelProperty(value = "设备类型（0视力表灯箱、1手机、2平板、3书桌、4电视、5台灯、6其他）", example = "0")
	private int deviceType;
	@ApiModelProperty(value = "最小年龄")
	private Integer minAge;
	@ApiModelProperty(value = "最大年龄")
	private Integer maxAge;
	@ApiModelProperty(value = "判定结果（null未判定 0正常 1警戒 2危险 3高危 4其他异常）", example = "0")
	private int judgementResult;
	@ApiModelProperty(value = "是否随机获取")
	private Boolean random = false;
	@JsonIgnore
	private String userId;
	@JsonIgnore
	private String ownerId;
	
    @ApiModel("响应-推荐搜索报告")
    @EqualsAndHashCode(callSuper=true)
    @Accessors(chain = true)
    @Data
    public static class RespRecommendReportList extends RespVisionReportInfo implements Serializable{
		private static final long serialVersionUID = -1826356033190200191L;
    }
}
