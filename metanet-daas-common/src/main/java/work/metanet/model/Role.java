package work.metanet.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "t_auth_role")
public class Role implements Serializable {
	
	private static final long serialVersionUID = -5190101949013404548L;
	
	/**
     * 角色id
     */
	@Id
    private String roleId;

    /**
     * 名称
     */
    private String roleName;
    
    private String roleType;

    /**
     * 备注-内部使用
     */
    private String remark;

    /**
     * 创建时间,默认系统时间,不需要手动插入
     */
    private Date createTime;

    /**
     * 数据有效性-0无效/1有效(实体类为boolean)
     */
    private Boolean status;
	
}
