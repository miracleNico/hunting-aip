package work.metanet.model;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.experimental.Accessors;

@Table(name = "t_region")
@Accessors(chain = true)
@Data
public class Region implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String regionId;
	
	private String name;

	private String pid;
	
	private Integer level;

}
