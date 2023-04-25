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
@Document(collection = "t_take_read_detail")
@Accessors(chain =  true)
@Data
public class TakeReadDetail implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    /**
     * 拍读明细id
     */
	@Id
    private String takeReadDetailId;
	
    /**
     * 用户id
     */
    private String userId;

    /**
     * 拍读id
     */
    private String takeReadId;
    
    /**
     * 学习内容
     */
    private String learningContent;
    
    /**
     * 翻译内容-单项
     */
    private String translationContent;
    
    /**
     *音频地址 
     */
    private String audioUrl;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 总分-中文或英文
     */
    private Double overallScore;
    
    /**
     * 流利度-英文
     */
    private Double overallFluencyScore;
    
    /**
     * 完整度-英文
     */
    private Double overallIntegrityScore;
    
    /**
     * 准确度-英文
     */
    private Double overallAccuracyScore;
    
    /**
     * 韵律度-英文
     */
    private Double overallRhythmScore;
    
}