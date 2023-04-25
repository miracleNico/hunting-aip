package work.metanet.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * t_serial_number
 * @author 
 */
@Data
@Table(name = "t_serial_number")
public class SerialNumber implements Serializable {
    /**
     * sn码
     */
	@Id
    private String snCode;

	/**
     * 使用状态-0未使用/1已使用
     */
    private Boolean useStatus;

    /**
     * 最大使用次数
     */
    private Integer maxUseNumber;

    /**
     * 使用次数
     */
    private Integer useNumber;

    /**
     * 产品id(仅适用于此产品)
     */
    private String appId;

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