package work.metanet.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@AllArgsConstructor
public enum ConstCacheKey {
	
	SMS(180),
	USER_TOKEN(60*60*24*30*3),
	RESOURCE(-1),
	SESSION(3600*4),
	GRADE(-1),
	PRESS(-1),
	SUBJECT(-1),
	BUSINESS_SN_CODE(-1),
	FEEDBACK_OPTION(-1),
	BUSINESS_SN_CODE_USE_FAIL_COUNT(60*60*24),
	BUSINESS_SN_CODE_GET_NULL_COUNT(60*60*24),
	UPGRADE(60*60*8),
	UPGRADE_THIRD_APP(-1),
	EDU_CONDITION(60*60*24),
	EDU_CONDITION_OTHER(60*60*24),
	EDU_CONDITION_THEME(60*60*24),
	EDU_CONDITION_DYNAMIC_ALL(60*60*24),
	FM_GROWTH_RECORD(60*60*24*30),
	LOCK(60),
	BLOOM_FILTER(-1),
	DEVICE_APP_ACTIVE(60*60*24*30),
	APP_SECRET(-1),
	STORE_APP(60),
	PRIZE_STORE(60*60),
	APP_SMS_SIGN(-1),
	USER_SCORE_EDU(60*60*24),
	ST_DAY_ACTIVE(60*60*24),
	REGION_ALL(-1),
	OPEN_APP(-1),
	OPEN_TOKEN(60*10),
	OPEN_OLD_TOKEN(5),
	OPEN_AUTH_NUMBER(60*60*24),
	VISION_CONDITION(60*60*24)
	;
	
	
	/**
	 * @Fields expire : seconds
	 */
	private long expire;
	
	public String cacheKey(String...ids) {
		StringBuffer sb = new StringBuffer();
		sb.append("LUOXI-CACHE").append(":").append(this.name());
		for (String id : ids) {
			sb.append(":").append(id);
		}
		return sb.toString();
	}
	
	
}
