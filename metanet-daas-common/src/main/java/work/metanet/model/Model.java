package work.metanet.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * t_channel_brand_model
 * @author 
 */
@Accessors(chain = true)
@Data
@Table(name = "t_model")
public class Model implements Serializable {
    /**
     * 型号id
     */
	@Id
    private String modelId;
	
	/**
     * 品牌id
     */
	private String brandId;

    /**
     * 型号名称
     */
    private String modelName;
    
    /**
     * 来源-0导入/1记录 
     */
    private String source;

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