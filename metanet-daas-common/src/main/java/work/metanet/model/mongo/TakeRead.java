package work.metanet.model.mongo;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description: 拍读
 * @Author wanbo
 * @DateTime 2019/12/19
 */
@Document(collection = "t_take_read")
@Accessors(chain =  true)
@Data
public class TakeRead implements Serializable {
    /**
     * 拍读id
     */
	@Id
    private String takeReadId;
	
	/**
	 * 拍读类型-BOOK/SCENE
	 */
	private String takeReadType;
	
	/**
	 * 拍读名称
	 */
	private String takeReadName;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 学习目录
     */
    private String learnDirId;

    /**
     * 学习语言
     */
    private String learningLanguage;

    /**
     * 翻译语言
     */
    private String translationLanguage;
    
    /**
     * 学习内容
     */
    private String learningContent;
    
    /**
     * 翻译内容-整体
     */
    private String translationContent;

    /**
     * 图片地址
     */
    private String imgUrl;

    /**
     * xmin
     */
    private Integer xmin;

    /**
     * ymin
     */
    private Integer ymin;

    /**
     * xmax
     */
    private Integer xmax;

    /**
     * ymax
     */
    private Integer ymax;
    
    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private String createTime;

    private static final long serialVersionUID = 1L;
}