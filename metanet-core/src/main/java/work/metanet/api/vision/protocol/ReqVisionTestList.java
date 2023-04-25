package work.metanet.api.vision.protocol;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import work.metanet.api.vision.protocol.ReqVisionTestInfo.RespVisionTestInfo;
import work.metanet.base.Paging;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author EdisonFeng
 * @Description 类ReqVisionTestList
 * @DateTime 2021年4月21日
 * Copyright(c) 2021. All Rights Reserved
 */
@ApiModel("请求-视力测试活动列表")
@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
@Data
public class ReqVisionTestList extends Paging implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4832427709639740239L;
	
	/**
     * 眼睛序列号
     */	
	@ApiModelProperty(value = "眼睛序列号", required = true, example = "fef63f64a83a11eb943f00ff71c9db07")
	private String eyeId;
	
    /**
     * 测试日期时间的开始值
     */	
	@ApiModelProperty(value = "测试日期和时间", example = "2021-04-01 12:30:59")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
	private Date beginTime;
    /**
     * 测试日期时间的结束值
     */	
	@ApiModelProperty(value = "测试日期和时间", example = "2021-05-01 12:30:59")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
	private Date endTime;	
//    /**
//     * 设备类型（0视力表灯箱、1手机、2平板、3书桌、4电视、5台灯、6其他）
//     */	
//	@ApiModelProperty(value = "设备类型（0视力表灯箱、1手机、2平板、3书桌、4电视、5台灯、6其他）", example = "0")
//	private int deviceType;
//	
//    /**
//     * 测试距离（单位米）
//     */	
//	@ApiModelProperty(value = "测试距离（单位米）", example = "5.0")
//	private double distance;
	
    /**
     * 测试序列号
     */	
	@ApiModelProperty(value = "测试序列号", example = "")
	private String testId;
    
    @ApiModel("响应-视力测试活动列表")
    @EqualsAndHashCode(callSuper=true)
    @Accessors(chain = true)
    @Data
    public static class RespVisionTestList extends RespVisionTestInfo implements Serializable{
    	/**
		 * serialVersionUID
		 */
		private static final long serialVersionUID = 6639410519989737696L;
		
//		private String appInfos;
    }
}
