package work.metanet.model;

import java.io.Serializable;

import javax.persistence.Table;

/**
 * @author Edison F.
 * @Description Model: 视疲劳记录t_eyestrain
 * @DateTime 2021/05/14
 */
@Table(name = "t_eyestrain")
public class Eyestrain extends EyestrainBase implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -773820713178468905L;

}
