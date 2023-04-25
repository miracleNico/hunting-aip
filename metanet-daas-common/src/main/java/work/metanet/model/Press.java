package work.metanet.model;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * t_press
 * @author 
 */
@Data
@Table(name = "t_press")
public class Press implements Serializable {
	
	@Id
    private Integer pressId;

    /**
     * 出版社名称
     */
    private String pressName;

    /**
     * 出版社简称
     */
    private String pressNameAbbr;

    private static final long serialVersionUID = 1L;
}