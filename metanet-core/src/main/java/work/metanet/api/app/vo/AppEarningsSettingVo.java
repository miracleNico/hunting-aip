package work.metanet.api.app.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class AppEarningsSettingVo implements Serializable{
	
	private static final long serialVersionUID = -2850611282439756876L;
	private String appId;
	private String thirdBusinessId;
	private Double settlementRatio;
	
}
