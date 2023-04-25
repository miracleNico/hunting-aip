package work.metanet.api.statistical.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class ChartVo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String name;
	private Integer value;

}
