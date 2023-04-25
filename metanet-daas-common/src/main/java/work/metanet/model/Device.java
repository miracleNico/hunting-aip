package work.metanet.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * t_device
 * @author 
 */
@Accessors(chain = true)
@Data
@Table(name = "t_device")
public class Device implements Serializable {
    /**
     * 设备id
     */
	@Id
    private String deviceId;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 有线mac
     */
    private String wiredMac;

    /**
     * 无线mac
     */
    private String wirelessMac;

    /**
     * IMEI号
     */
    private String imei;

    /**
     * IMEI2号
     */
    private String imei2;

    /**
     * 蓝牙
     */
    private String bluetooth;

    /**
     * 型号id
     */
    private String modelId;

    /**
     * 品牌id
     */
    private String brandId;
    
    /**
     * 终端序列号
     */
    private String serialNumber;

    /**
     * 终端uuid
     */
    private String uuid;

    /**
     * 固件信息
     */
    private String firmwareInfo;

    /**
     * 启用状态-0禁用/1启用
     */
    private Boolean enableStatus;

    /**
     * 备注-内部使用
     */
    private String remark;
    
    /**
     * 来源-0导入/1记录 
     */
    private String source;

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