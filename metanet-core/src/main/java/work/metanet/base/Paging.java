package work.metanet.base;

import java.io.Serializable;

import javax.validation.constraints.Min;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("请求-分页信息")
@Data
public class Paging implements Serializable {
	
	private static final long serialVersionUID = 6198021117793755208L;
	
    @ApiModelProperty(value = "当前页码,默认第1页",required = true)
    @Min(value = 1,message = "页码格式错误")
	private Integer pageNum = 1;
	
    @ApiModelProperty(value = "每页条数,默认10条",required = true)
    @Min(value = 1,message = "每页条数格式错误")
	private Integer pageSize = 10;
    
}
