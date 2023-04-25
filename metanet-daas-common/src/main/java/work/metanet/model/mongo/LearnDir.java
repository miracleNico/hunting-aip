package work.metanet.model.mongo;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description: 学习目录
 * @Author wanbo
 * @DateTime 2019/12/19
 */
@Document(collection = "t_learn_dir")
@Accessors(chain =  true)
@Data
public class LearnDir implements Serializable {
    /**
     * 学习目录id
     */
	@Id
    private String learnDirId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 目录名称
     */
    private String dirName;
    
    /**
     * 目录类型
     */
    private String dirType;

    /**
     * 序号
     */
    private Integer sort;

    /**
     * 父级id
     */
    private String parentId;

    /**
     * 创建时间,默认系统时间,不需要手动插入
     */
    private String createTime;

    private static final long serialVersionUID = 1L;
}