package work.metanet.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "t_auth_permission")
public class Permission implements Serializable {
	
	private static final long serialVersionUID = -5190101949013404548L;
	
	/**
     * 权限id
     */
	@Id
    private String permissionId;

    /**
     * 名称
     */
    private String permissionName;
    
    /**
     * 权限类型-'MODULE','MENU','BTN','FN'
     */
    private String permissionType;
    
    private String permissionTag;
    
    private String parentId;
    
    private String apis;
    
    private Integer sort;
    
    private String icon;

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
