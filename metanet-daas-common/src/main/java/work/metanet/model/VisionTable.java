package work.metanet.model;

import java.io.Serializable;

import javax.persistence.Table;
/**
 * @author Edison F.
 * @Description Model: 视力表t_vision_table
 * @DateTime 2021/05/01
 */
@Table(name = "t_vision_report")
public class VisionTable extends VisionTableBase implements Serializable {
	private static final long serialVersionUID = 4173410879728730103L;

}
