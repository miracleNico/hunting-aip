package work.metanet.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * t_channel_brand
 * @author 
 */
@Accessors(chain = true)
@Data
@Table(name = "t_brand")
public class Brand implements Serializable {
    /**
     * 品牌id
     */
	@Id
    private String brandId;

    /**
     * 品牌名称
     */
    private String brandName;

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