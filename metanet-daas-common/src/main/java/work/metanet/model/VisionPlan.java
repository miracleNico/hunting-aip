
package work.metanet.model;

import java.io.Serializable;

import javax.persistence.Table;

/**
 * @author Edison F.
 * @Description Model: 视力计划t_vision_plan
 * @DateTime 2021/05/14
 */
@Table(name = "t_vision_plan")
public class VisionPlan extends VisionPlanBase  implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6832022614391935912L;

}
