package work.metanet.api.visionContent.protocol;

import work.metanet.base.Paging;
import work.metanet.model.VisionContentBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
@Data
public class ReqVisionContentList extends Paging implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 内容名称
	 */
	@ApiModelProperty(value = "内容名称")
	private String name;

	/**
	 * 摘要
	 */
	@ApiModelProperty(value = "摘要")
	private String digest;

	/**
	 * 关键词
	 */
	@ApiModelProperty(value = "关键词")
	private String keyword;

	/**
	 * 行业
	 */
	@ApiModelProperty(value = "行业")
	private int industry;
	/**
	 * 锻炼类型
	 */
	@ApiModelProperty(value = "锻炼类型（-1所有类型、0眼保健操、1眼肌锻炼、2眼球放松、3新型眼操、4其他）")
	private int type1;

	/**
	 * 锻炼模式
	 */
	@ApiModelProperty(value = "锻炼模式（-1所有模式，0自我监督模式，1家长协助模式，2医生协助模式）")
	private int type2;

	/**
	 * 判定结果
	 */
	@ApiModelProperty(value = "判定结果（null未判定 0正常 1警戒 2危险 3高危 4其他异常）")
	private int type3;

	/**
	 * 内容来源
	 */
	@ApiModelProperty(value = "来源（-1未知，0原创，1转载，2翻译，3其他）")
	private int type4;



	@EqualsAndHashCode(callSuper=true)
	@Data
	public static class RespVisionContentList extends VisionContentBase implements Serializable{
		private static final long serialVersionUID = -5255740365569977577L;
	}
	
}
