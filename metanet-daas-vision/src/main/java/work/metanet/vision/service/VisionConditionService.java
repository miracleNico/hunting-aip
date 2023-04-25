
package work.metanet.vision.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import work.metanet.api.vision.IVisionCondition;
import work.metanet.api.vision.protocol.ReqVisionCondition;
import work.metanet.api.vision.protocol.ReqVisionCondition.RespVisionCondition;
import work.metanet.api.vision.protocol.ReqVisionCondition.RespVisionConditionKV;
import work.metanet.constant.ConstCacheKey;
import work.metanet.constant.ConstDistance;
import work.metanet.constant.ConstEyeType;
import work.metanet.constant.constAgeGroup;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;

/**
 * @Description 视力筛选条件服务
 * @author EdisonFeng
 * @DateTime 2021年4月25日
 * Copyright(c) 2021. All Rights Reserved
 */
@DubboService
public class VisionConditionService implements IVisionCondition {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Override
	public List<RespVisionCondition> getVisionCondition(ReqVisionCondition req) throws Exception {
		List<RespVisionCondition> respList = new ArrayList<RespVisionCondition>();
		ConstCacheKey constCacheKey = ConstCacheKey.VISION_CONDITION;
		String key = constCacheKey.cacheKey(Integer.toString(req.getTestMode()));
		if(stringRedisTemplate.hasKey(key)) {
			respList = JSONUtil.toList(new JSONArray(stringRedisTemplate.opsForValue().get(key)), RespVisionCondition.class);
		}else {
			List<RespVisionConditionKV> ageList = CollUtil.newArrayList(
					new RespVisionConditionKV().setKey(constAgeGroup.BIRTH.getKey()).setVal(constAgeGroup.BIRTH.getAgeRange()),
					new RespVisionConditionKV().setKey(constAgeGroup.ONE_MONTH.getKey()).setVal(constAgeGroup.ONE_MONTH.getAgeRange()),
					new RespVisionConditionKV().setKey(constAgeGroup.SIX_MONTHS.getKey()).setVal(constAgeGroup.SIX_MONTHS.getAgeRange()),
					new RespVisionConditionKV().setKey(constAgeGroup.ONE_YEAR.getKey()).setVal(constAgeGroup.ONE_YEAR.getAgeRange()),
					new RespVisionConditionKV().setKey(constAgeGroup.THREE_YEARS.getKey()).setVal(constAgeGroup.THREE_YEARS.getAgeRange()),
					new RespVisionConditionKV().setKey(constAgeGroup.SIX_YEARS.getKey()).setVal(constAgeGroup.SIX_YEARS.getAgeRange()),
					new RespVisionConditionKV().setKey(constAgeGroup.EIGHT_YEARS.getKey()).setVal(constAgeGroup.EIGHT_YEARS.getAgeRange()),
					new RespVisionConditionKV().setKey(constAgeGroup.FOURTEEN_YEARS.getKey()).setVal(constAgeGroup.FOURTEEN_YEARS.getAgeRange()),
					new RespVisionConditionKV().setKey(constAgeGroup.TWENTY_YEARS.getKey()).setVal(constAgeGroup.TWENTY_YEARS.getAgeRange()),
					new RespVisionConditionKV().setKey(constAgeGroup.ADULT.getKey()).setVal(constAgeGroup.ADULT.getAgeRange()),
					new RespVisionConditionKV().setKey(constAgeGroup.OLD_AGE.getKey()).setVal(constAgeGroup.OLD_AGE.getAgeRange())
					);
			List<RespVisionConditionKV> distanceList = CollUtil.newArrayList(
					new RespVisionConditionKV(ConstDistance.CLOSE.getKey(), Double.toString(ConstDistance.CLOSE.getValue())),
					new RespVisionConditionKV(ConstDistance.MEDIUM.getKey(), Double.toString(ConstDistance.MEDIUM.getValue())),
					new RespVisionConditionKV(ConstDistance.DISTINT.getKey(), Double.toString(ConstDistance.DISTINT.getValue()))
					);
			List<RespVisionConditionKV> eyeTypeList = CollUtil.newArrayList(
					new RespVisionConditionKV(ConstEyeType.LEFT_EYE.getText(), "左眼"),
					new RespVisionConditionKV(ConstEyeType.RIGHT_EYE.getText(), "右眼")
					);
			
			CollUtil.addAll(respList, Arrays.asList(
					new RespVisionCondition().setType("age").setList(ageList),
					new RespVisionCondition().setType("distance").setList(distanceList),
					new RespVisionCondition().setType("eyeType").setList(eyeTypeList)
					));
			
			stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(respList),Duration.ofSeconds(constCacheKey.getExpire()));
		}
		return respList;
	}

}
