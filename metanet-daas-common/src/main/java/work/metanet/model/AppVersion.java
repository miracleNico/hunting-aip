package work.metanet.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * t_app_version
 * @author 
 */
@Data
@Table(name = "t_app_version")
public class AppVersion implements Serializable {

	/**
	 * 版本id
	 */
	@Id
	private String versionId;

    /**
     * 产品id
     */
    private String appId;

	/**
	 * 版本号
	 */
	private String versionCode;
	
    /**
     * 版本名称
     */
    private String versionName;
    
    /**
     * 版本类型
     */
    private String versionType;
    
    /**
     * 文件大小
     */
    private BigDecimal fileSize;
    
    private String md5;
    
    private String url;
    
    /**
     * 文件地址类型-0外部/1内部
     */
    private String urlType;

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
     * 更新说明
     */
    private String instruction;

    private static final long serialVersionUID = 1L;
}