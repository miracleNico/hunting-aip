package work.metanet.api.vision.protocol;

import java.io.Serializable;

import work.metanet.api.vision.protocol.ReqEyeInfo.RespEyeInfo;
import work.metanet.base.Paging;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * @author Edison F.
 * @Description 请求-眼睛列表
 * @DateTime 2021/04/20
 */
@ApiModel("请求-眼睛列表")
@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
@Data
public class ReqEyeList extends Paging implements Serializable {
	private static final long serialVersionUID = -424544866552911999L;

	@ApiModelProperty(value = "类型（0左眼,1右眼）")
	private int type;
	
	@ApiModelProperty(value = "用户序列号，关联用户信息", example = "e7e6dfb0a83911eb943f00ff71c9db07")
	private String userId;

//	
//	@ApiModelProperty(value = "眼睛高度（毫米）")
//	private int height;
//	
//	@ApiModelProperty(value = "眼睛宽度（毫米）")
//	private int width;
//	
//	@ApiModelProperty(value = "瞳距（毫米）")
//	private String pupiDistance;
//	
//	@ApiModelProperty(value = "眼轴（毫米）")
//	private String lengthOfOpticAxis;
//	
//	@ApiModelProperty(value = "瞳孔直径（毫米）")
//	private String pupiDiameter;  
	
    
    @ApiModel("响应-眼睛列表")
    @EqualsAndHashCode(callSuper=true)
    @Accessors(chain = true)
    @Data
    public static class RespEyeList extends RespEyeInfo implements Serializable{
    	private static final long serialVersionUID = 7419211972001277025L;
    	
//    	private String appInfos;
    }
}
