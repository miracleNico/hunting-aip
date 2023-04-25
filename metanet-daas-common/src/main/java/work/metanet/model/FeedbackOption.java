package work.metanet.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * t_feedback_option
 * @author 
 */
@Data
@Table(name="t_feedback_option")
public class FeedbackOption implements Serializable {
    /**
     * 反馈选项id
     */
	@Id
    private String feedbackOptionId;

    /**
     * 反馈选项内容
     */
    private String feedbackOptionContent;

    /**
     * 父级内容
     */
    private String parentId;

    /**
     * 备注-内部使用
     */
    private String remark;

    /**
     * 创建时间,默认系统时间,不需要手动插入
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}