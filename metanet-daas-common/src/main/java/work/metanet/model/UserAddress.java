package work.metanet.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * t_user_address
 * @author 
 */
@Table(name = "t_user_address")
@Accessors(chain = true)
@Data
public class UserAddress implements Serializable {
    /**
     * id
     */
	@Id
    private String userAddressId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 收货人
     */
    private String consignee;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 省id
     */
    private String provinceId;

    /**
     * 市id
     */
    private String cityId;

    /**
     * 县id
     */
    private String countyId;

    /**
     * 收货地址
     */
    private String address;

    /**
     * 邮编
     */
    private String zipCode;

    /**
     * 默认状态
     */
    private Boolean defaultStatus;

    /**
     * 创建时间,默认系统时间,不需要手动插入
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}