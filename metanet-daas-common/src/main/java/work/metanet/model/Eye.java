package work.metanet.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Table;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Edison F.
 * @Description Model: 眼睛t_eye
 * @DateTime 2021/04/20
 */

@Table(name = "t_eye")
public class Eye extends EyeBase implements Serializable {

	/**
	 * UID序列号
	 */
	private static final long serialVersionUID = -996693793078157718L;

}
