package work.metanet.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * t_user
 * @author 
 */
@Accessors(chain = true)
@Data
@Table(name = "t_user")
public class User implements Serializable {
    /**
     * 用户id
     */
	@Id
    private String userId;
	
	private String appId;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 省
     */
    private String provinceId;

    /**
     * 城市
     */
    private String cityId;
    
    /**
     * 县
     */
    private String countyId;
    
    /**
     * 邮编
     */
    private String zipCode;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别-0女/1男/2保密
     */
    private String gender;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 爱好-多个用/分割
     */
    private String hobby;

    /**
     * 头像路径
     */
    private String headUrl;

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
     * 启用状态-0禁用/1启用
     */
    private Boolean enableStatus;

    /**
     * 数据有效性-0无效/1有效(实体类为boolean)
     */
    private Boolean status;

    private static final long serialVersionUID = 1L;
}