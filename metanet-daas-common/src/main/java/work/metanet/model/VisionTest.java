package work.metanet.model;

import java.io.Serializable;

import javax.persistence.Table;

/**
 * @author Edison F.
 * @Description Model: 视力测试活动t_vision_test
 * @DateTime 2021/04/20
 */
@Table(name = "t_vision_test")
public class VisionTest extends VisionTestBase implements Serializable {

	/**
	 * UID序列号
	 */
	private static final long serialVersionUID = -3956398204215190488L;

}