package work.metanet.api.statistical.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ActiveUserAppVo implements Serializable{
	private static final long serialVersionUID = 6142654679130373333L;
	private String name;
	private String type = "line";
	private Boolean smooth = true;
	private List<Integer> data = new ArrayList<Integer>();

}
