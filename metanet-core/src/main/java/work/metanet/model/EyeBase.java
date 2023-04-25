package work.metanet.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
/**
 * @author Edison F.
 * @Description Model: 眼睛t_eye 的基类
 * @DateTime 2021/04/20
 */
@Accessors(chain = true)
@Data
public class EyeBase extends Base {
	/**
     * 用户序列号，关联用户信息
     */	
	@ApiModelProperty(value = "用户序列号，关联用户信息", example = "e7e6dfb0a83911eb943f00ff71c9db07")
	private String userId;
	
    /**
     * 眼睛拥有者ID，用户ID，来自第三方系统，后台自动创建用户到本系统
     */	
	@ApiModelProperty(value = "用户ID，来自第三方系统", example = "1234567890")
	private String ownerId;
	
    /**
     * 眼睛拥有者姓名，用户姓名，来自第三方系统
     */	
	@ApiModelProperty(value = "用户姓名，来自第三方系统", example = "Edison Feng")
	private String ownerName;
	
    /**
     * 眼睛拥有者电话，用户电话，来自第三方系统
     */	
	@ApiModelProperty(value = "用户电话，来自第三方系统", example = "13590909090")
	private String ownerTel;
	
    /**
     * 眼睛拥有者年龄，用户年龄，来自第三方系统
     */	
	@ApiModelProperty(value = "用户年龄，来自第三方系统", example = "16")
	private int ownerAge;
	
    /**
     * 眼睛拥有者身高，用户身高，单位厘米，来自第三方系统
     */	
	@ApiModelProperty(value = "用户身高，单位厘米，来自第三方系统", example = "170")
	private double ownerHeight;
	
//    /**
//     * 用户姓名，来自本系统
//     */	
//	@ApiModelProperty(value = "用户姓名，来自本系统", example = "Edison Feng")
//	@NotBlank(message = "用户姓名")
//	private String userName;
//	
//    /**
//     * 用户电话，来自本系统
//     */	
//	@ApiModelProperty(value = "用户电话，来自本系统", example = "13590909090")
//	@NotBlank(message = "用户电话")
//	private String phone;

	/**
     * 类型（0左眼,1右眼）
     */	
	@ApiModelProperty(value = "类型（0左眼,1右眼）", example = "0")
	private int type;
	
//    /**
//     * 眼睛高度（毫米）
//     */	
//	@ApiModelProperty(value = "眼睛高度（毫米）", example = "0")
//	private double height;
//	
//    /**
//     * 眼睛宽度（毫米）
//     */	
//	@ApiModelProperty(value = "眼睛宽度（毫米）", example = "0")
//	private double width;
//	
//    /**
//     * 瞳距（毫米）
//     */	
//	@ApiModelProperty(value = "瞳距（毫米）", example = "0")
//	private double pupiDistance = 0;
//	
//    /**
//     * 眼轴（毫米）
//     */	
//	@ApiModelProperty(value = "眼轴（毫米）", example = "0")
//	private double lengthOfOpticAxis = 0;
//    /**
//     * 角膜直径（毫米）
//     */	
//	@ApiModelProperty(value = "角膜直径（毫米）", example = "0")
//	private double cornealDiameter = 0;    	
//    /**
//     * 角膜厚度（微米）
//     */	
//	@ApiModelProperty(value = "角膜厚度（微米）", example = "0")
//	private double cornealThickness = 0;    	
//    /**
//     * 瞳孔直径（毫米）
//     */	
//	@ApiModelProperty(value = "瞳孔直径（毫米）", example = "0")
//	private double pupiDiameter = 0;    	
//    /**
//     * 眼压IOP（mmHg）
//     */	
//	@ApiModelProperty(value = "眼压IOP（mmHg）", example = "0")
//	private double intraocularPressure = 0;    	
//    /**
//     * 曲率半径（毫米）
//     */	
//	@ApiModelProperty(value = "曲率半径（毫米）", example = "0")
//	private double radiusCurvature = 0;
//	
//    /**
//     * 介质折射率
//     */	
//	@ApiModelProperty(value = "介质折射率", example = "0")
//	private double refractiveIndexForEyeball = 0;
//	
//    /**
//     * 视网膜曲率半径（毫米）
//     */	
//	@ApiModelProperty(value = "视网膜曲率半径（毫米）", example = "0")
//	private double radiusCurvatureForRetina = 0;
//	
//    /**
//     * 强光光圈
//     */	
//	@ApiModelProperty(value = "强光光圈", example = "0")
//	private double apertureForHighLight = 0;
//	
//    /**
//     * 低光光圈
//     */	
//	@ApiModelProperty(value = "低光光圈", example = "0")
//	private double apertureForLowLight = 0;
//	
//    /**
//     * 物方焦距（毫米）
//     */	
//	@ApiModelProperty(value = "物方焦距（毫米）", example = "0")
//	private double focusDistanceForObjectSpace = 0;
//	
//    /**
//     * 像方焦距（毫米）
//     */	
//	@ApiModelProperty(value = "像方焦距（毫米）", example = "0")
//	private double focusDistanceForImageSpace = 0;
//	
//    /**
//     * 像方光焦度
//     */	
//	@ApiModelProperty(value = "像方光焦度", example = "0")
//	private double focalPowerForImageSpace;
	
	/**
     * 眼睛序列号（修改时必需）
     */	
	@ApiModelProperty(value = "眼睛序列号（修改时必需）", example = "48a66898a83c11eb943f00ff71c9db07")
	@Id
	private String eyeId;
}