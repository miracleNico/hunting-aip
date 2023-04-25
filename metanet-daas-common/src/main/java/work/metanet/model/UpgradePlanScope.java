package work.metanet.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * t_upgrade_plan_scope
 * @author 
 */
@Data
@Table(name = "t_upgrade_plan_scope")
public class UpgradePlanScope implements Serializable {
    /**
     * 主键id
     */
	@Id
    private String upgradePlanScopeId;

    /**
     * 升级计划id
     */
    private String upgradePlanId;

    /**
     * 品牌id
     */
    private String brandId;

    /**
     * 型号范围-0所有/1指定
     */
    private String modelScope;
    
    /**
     * 型号
     */
    private String models;

    /**
     * 创建时间,默认系统时间,不需要手动插入
     */
    private Date createTime;
    
    private static final long serialVersionUID = 1L;
}