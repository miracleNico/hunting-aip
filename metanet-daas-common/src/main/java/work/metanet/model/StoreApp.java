package work.metanet.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * t_store_app
 * @author 
 */
@Data
@Table(name = "t_store_app")
public class StoreApp implements Serializable {
    /**
     * 应用商店id
     */
	@Id
    private String storeAppId;
	
	private String channelId;

    /**
     * 应用名称
     */
    private String appName;
    
    private String icon;

    /**
     * 包名
     */
    private String packageName;
    
    /**
     * 调用类名
     */
    private String callClass;

    /**
     * 文件大小(b)字节
     */
    private BigDecimal fileSize;
    
    /**
     * 版本名
     */
    private String versionName;

    /**
     * 版本号
     */
    private String versionCode;

    /**
     * 应用范围-0所有/1指定
     */
    private String appScope;

    /**
     * 下载次数
     */
    private Integer downloadNumber;

    /**
     * 发布时间
     */
    private Date releaseTime;

    /**
     * 开发商
     */
    private String developer;
    
    /**
     * 文件地址
     */
    private String url;

    /**
     * 应用说明
     */
    private String instruction;

    /**
     * 应用截图
     */
    private String images;
    
    private String md5;
    
    private Boolean enable;

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