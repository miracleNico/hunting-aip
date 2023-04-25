package work.metanet.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * t_device_app_business_sn_code
 * @author 
 */
@Accessors(chain = true)
@Data
@Table(name = "t_device_app_business_sn_code")
public class DeviceAppBusinessSnCode implements Serializable {
    /**
     * 主键id
     */
	@Id
    private String unionId;

    /**
     * 设备产品id
     */
    private String deviceAppId;

    /**
     * 内容商激活码id
     */
    private String businessSerialNumberId;
    
    /**
     * 使用状态
     */
    private String useStatus;

    /**
     * 创建时间,默认系统时间,不需要手动插入
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}