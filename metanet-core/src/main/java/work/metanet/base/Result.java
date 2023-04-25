package work.metanet.base;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@ApiModel("响应")
@Data
@Accessors(chain = true)
@RequiredArgsConstructor(staticName = "of")
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable{
	
	private static final long serialVersionUID = -255097391053827184L;
	
	@ApiModelProperty("状态")
	@NonNull
	private Boolean success;
	
	@ApiModelProperty("状态码")
	private int code;
    
	@ApiModelProperty("描述")
	@NonNull
	private String msg;
	
	@ApiModelProperty("数据")
    private T data;
    
    public Result(ResultMessage resultMessage) {
    	this.success = resultMessage.isSuccess();
    	this.code = resultMessage.getCode();
    	this.msg = resultMessage.getMsg();
    }
    
}
