package work.metanet.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * t_statistical_app_active
 * @author 
 */
@Data
@Table(name = "t_statistical_app_active")
public class StatisticalAppActive implements Serializable {
	@Id
    private String id;
	
	private Date date;
	
    private Integer number;

    private static final long serialVersionUID = 1L;
}