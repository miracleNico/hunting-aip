package work.metanet.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class ImportMsgVo implements Serializable{
	
	private static final long serialVersionUID = -6027654882938003894L;
	
	private Integer totalCount = 0;
	private Integer successCount = 0;
	private Integer failCount = 0;
	private List<ImportFailVo> fails;
	
	
	@Accessors(chain = true)
	@Data
	public static class ImportFailVo implements Serializable{
		
		private static final long serialVersionUID = -7450950781020637421L;
		
		private Integer row;
		private Integer col;
		private String msg;
		
	}
	
}
