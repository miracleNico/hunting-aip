package work.metanet.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * t_upgrade_plan
 * @author 
 */
@Data
@Table(name = "t_upgrade_plan")
public class UpgradePlan implements Serializable {
    /**
     * 升级计划id
     */
	@Id
    private String upgradePlanId;

    /**
     * 升级名称
     */
    private String upgradePlanName;

    /**
     * 升级时间-默认当前时间
     */
    private Date upgradeTime;

    /**
     * 产品id
     */
    private String appId;

    /**
     * 产品版本范围start
     */
    private String startVersionId;

    /**
     * 产品版本范围end
     */
    private String endVersionId;

    /**
     * 升级至产品版本
     */
    private String toVersionId;

    /**
     * 设备范围-0所有/1指定
     */
    private String deviceScope;

    /**
     * 是否强制升级-0否/1是
     */
    private Boolean enforce;

    /**
     * 发布状态-0未发布/1已发布
     */
    private Boolean sendStatus;

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