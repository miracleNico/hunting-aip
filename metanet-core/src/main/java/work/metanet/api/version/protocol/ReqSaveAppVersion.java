package work.metanet.api.version.protocol;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@Data
public class ReqSaveAppVersion implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	
	private String versionId;
	@NotBlank(message = "版本号不能为空")
	private String versionCode;
	@NotBlank(message = "版本名称不能为空")
	private String versionName;
	private BigDecimal fileSize;
	private String md5;
	@NotBlank(message = "文件地址不能为空")
    private String url;
	@NotBlank(message = "产品id不能为空")
	private String appId;
	@NotBlank(message = "版本类型不能为空")
	private String versionType;
	private String instruction;
    private String remark;
	
    
	@EqualsAndHashCode(callSuper=true)
    @Accessors(chain = true)
    @Data
    public static class RespSaveAppVersion extends ReqSaveAppVersion implements Serializable{
    	
    	private static final long serialVersionUID = -4291721599975198242L;
    	
    	
    	
    }
    

}
