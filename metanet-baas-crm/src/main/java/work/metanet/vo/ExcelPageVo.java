package work.metanet.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class ExcelPageVo implements Serializable{
	
	private static final long serialVersionUID = -2772304063978057505L;
	
	private Integer page;
	private String unit;
	private String title;
	private String lesson;
	private String section;
	private Integer contentNo;
	private String content;
	private String translation;
	private Integer audioType;
	
	
}