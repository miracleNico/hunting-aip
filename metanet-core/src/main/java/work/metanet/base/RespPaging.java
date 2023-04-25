package work.metanet.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@ApiModel("分页列表")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper=true)
@Data
public class RespPaging<T> extends Paging implements Serializable {
	
	private static final long serialVersionUID = -7393402519625425L;

	@ApiModelProperty(value = "结果记录总数")
    private long total;

    @ApiModelProperty(value = "总页数")
    private int pages;
    
    @ApiModelProperty(value = "额外属性")
    private Map<String, Object> extra;

    @ApiModelProperty(value = "当前页结果列表")
    private List<T> list;
    
}
