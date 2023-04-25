package work.metanet.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * t_app
 * @author 
 */
@Accessors(chain = true)
@Data
@Table(name = "t_app")
public class App implements Serializable {
    /**
     * 产品id
     */
	@Id
    private String appId;
	
	/**
	 * 渠道id
	 */
	private String channelId;
	
	/**
     * 认证方式
     */
    private String authType;

    /**
     * 产品名称
     */
    private String appName;
    
    /**
     * 包名
     */
    private String packageName;
    

    /**
     * 软件类型-0APP/1固件
     */
    private String appType;
    
    /**
     * 启用SN激活
     */
    private Boolean enableSn;
    
    private String appKey;
    
    private String appSecret;

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
     * 产品说明
     */
    private String instruction;

    private static final long serialVersionUID = 1L;
}