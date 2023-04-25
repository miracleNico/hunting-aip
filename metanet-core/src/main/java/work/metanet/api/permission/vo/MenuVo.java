package work.metanet.api.permission.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class MenuVo implements Serializable{
	
	private static final long serialVersionUID = 5155565031871776679L;
	private String id;
	private String title;
	private String href;
	private String parentId;
	private String icon;
	private String target = "_self";
	private List<MenuVo> children;

}
