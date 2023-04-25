package work.metanet.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class BookVo implements Serializable{
	
	private static final long serialVersionUID = -6335360556023673407L;
	private String bookId;
	private String bookName;
	private String subject;
	private String press;
	private String grade;
	private String volume;
	private String detail;
	private List<BookPageVo> pages = new ArrayList<BookPageVo>();
	
	@Accessors(chain = true)
	@Data
	public static class BookPageVo implements Serializable{
		
		private static final long serialVersionUID = -2772304063978057505L;
		private Integer page;
		private String title;
		private Integer width;
		private Integer height;
		private String audioZipUrl;
		private List<BookPageContentVo> contents = new ArrayList<BookPageContentVo>();
		
	}
	
	@Accessors(chain = true)
	@Data
	public static class BookPageContentVo implements Serializable{
		
		private static final long serialVersionUID = -2338204153195902828L;
		private Integer contentNo;
		private Integer xmin;
		private Integer xmax;
		private Integer ymin;
		private Integer ymax;
		private String section;
		private String content;
		private String translation;
		private String audioFile;
		private Integer audioType;
		
	}
	
}
