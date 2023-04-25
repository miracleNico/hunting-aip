package work.metanet.api.vision.protocol;

import java.io.Serializable;

import work.metanet.model.VisionTestBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author EdisonFeng
 * @Description 类ReqSaveVisionTest
 * @DateTime 2021年4月21日
 * Copyright(c) 2021. All Rights Reserved
 */
public class ReqSaveVisionTest extends VisionTestBase implements Serializable {

	private static final long serialVersionUID = 3915694107724267188L;
	
	@ApiModel("响应-保存测试活动信息")
    @Accessors(chain = true)
    @Data
    public static class RespSaveVisionTest implements Serializable {

		private static final long serialVersionUID = 4255561977588973812L;

		/**
	     * 测试活动序列号
	     */	
		@ApiModelProperty(value = "测试活动序列号", example = "e7e6dfb0a83911eb943f00ff71c9db07")
		private String testId;
		
	    /**
	     * 测试距离（单位米）
	     */	
		@ApiModelProperty(value = "测试距离（单位米）", example = "2.0")
		private double distance;

		/**
	     * 测试模式（0自测模式，1家长协助模式，2医生协助模式）
	     */	
		@ApiModelProperty(value = "测试模式（0自测模式，1家长协助模式，2医生协助模式）", example = "0")
		private int testMode;
		
		/**
	     * 眼睛序列号
	     */	
		@ApiModelProperty(value = "眼睛序列号", example = "48a66898a83c11eb943f00ff71c9db07")
		private String eyeId;
    }
}
