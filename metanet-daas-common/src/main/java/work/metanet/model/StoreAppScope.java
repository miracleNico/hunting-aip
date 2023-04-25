package work.metanet.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * t_store_app_scope
 * @author 
 */
@Data
@Table(name = "t_store_app_scope")
public class StoreAppScope implements Serializable {
    /**
     * 主键id
     */
	@Id
    private String storeAppScopeId;

    /**
     * 应用商店id
     */
    private String storeAppId;

    /**
     * 产品id
     */
    private String appId;

    /**
     * 创建时间,默认系统时间,不需要手动插入
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}