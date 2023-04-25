package work.metanet.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * t_feedback
 * @author 
 */
@Data
@Table(name="t_feedback")
public class Feedback implements Serializable {
    /**
     * 反馈id
     */
	@Id
    private String feedbackId;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 版本
     */
    private String versionCode;

    /**
     * qq
     */
    private String qq;

    /**
     * vx
     */
    private String vx;

    /**
     * tel
     */
    private String tel;

    /**
     * 选项1
     */
    private String feedbackOptionContent1;

    /**
     * 选项2
     */
    private String feedbackOptionContent2;
    
    private String uuid;
    
    private String mac;
    /**
     * @Fields 品牌 : 
     */
    private String brandName;
    /**
     * @Fields 型号 : 
     */
    private String modelName;
    
    /**
     * 处理状态 
     */
    private String processStatus;
    
    /**
     * 备注-内部使用
     */
    private String remark;

    /**
     * 创建时间,默认系统时间,不需要手动插入
     */
    private Date createTime;

    /**
     * 创建者id
     */
    private String createUser;

    /**
     * 修改者id
     */
    private String updateUser;

    /**
     * 数据有效性-0无效/1有效(实体类为boolean)
     */
    private Boolean status;

    /**
     * 内容
     */
    private String content;

    private static final long serialVersionUID = 1L;
}