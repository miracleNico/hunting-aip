package work.metanet.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * t_app_version_module
 * @author 
 */
@Data
@Table(name = "t_app_version_module")
public class AppVersionModule implements Serializable {
    /**
     * 版本模块id
     */
	@Id
    private String versionModuleId;
	
	private String versionId;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 调用类型
     */
    private String callType;

    /**
     * 父级id
     */
    private String parentId;
    
    /**
     * 调用包名
     */
    private String packageName;

    /**
     * 调用类名
     */
    private String className;

    /**
     * 参数
     */
    private String parameter;
	
	/**
     * 图标
     */
    private String moduleIcon;

    /**
     * 序号
     */
    private Integer sort;

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

    private static final long serialVersionUID = 1L;
}