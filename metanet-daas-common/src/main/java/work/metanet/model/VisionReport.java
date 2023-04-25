package work.metanet.model;

import java.io.Serializable;

import javax.persistence.Table;

/**
 * @author Edison F.
 * @Description Model: 视力报告t_vision_report
 * @DateTime 2021/04/20
 */
@Table(name = "t_vision_report")
public class VisionReport extends VisionReportBase implements Serializable {

	/**
	 * UID序列号
	 */
	private static final long serialVersionUID = -7833643003036705655L;


}
