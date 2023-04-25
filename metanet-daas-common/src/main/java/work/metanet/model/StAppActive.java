package work.metanet.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Table(name = "t_statistical_app_active")
public class StAppActive implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
    private String date;
    
    private String appId;
    
    private Integer number;

    private static final long serialVersionUID = 1L;
}