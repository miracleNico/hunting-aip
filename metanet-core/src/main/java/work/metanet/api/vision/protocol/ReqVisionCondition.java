package work.metanet.api.vision.protocol;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description 请求-视力报告筛选条件
 * @author EdisonFeng
 * @DateTime 2021年4月23日
 * Copyright(c) 2021. All Rights Reserved
 */

@ApiModel("请求-视力报告筛选条件")
@Accessors(chain = true)
@Data
public class ReqVisionCondition implements Serializable {
	/**
	 * UID序列号
	 */
	private static final long serialVersionUID = 6398303449213996296L;

	@ApiModelProperty(value = "测试模式（0自测模式，1家长协助模式，2医生协助模式）", required = true, example = "0")
	@Pattern(regexp = "^(0|1|2)$", message = "测试模式格式错误")
	private int testMode;
	
	@ApiModel("响应-视力报告筛选条件类型")
	@Accessors(chain = true)
	@Data
	public static class RespVisionCondition implements Serializable {
		private static final long serialVersionUID = 6468358973358063511L;
		private String type;
		private List<RespVisionConditionKV> list;
	}
	
	@ApiModel("响应-视力报告筛选条件键值列表")
	@Accessors(chain = true)
	@Data
	public static class RespVisionConditionKV implements Serializable {
		private static final long serialVersionUID = 8956289349107813273L;
		private String key;
		private String val;

		public RespVisionConditionKV(String key, String val) {
			this.key = key;
			this.val = val;
		}
		
		public RespVisionConditionKV() {

		}
	}
}

