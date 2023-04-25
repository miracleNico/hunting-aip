package work.metanet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.google.common.base.Charsets;
import com.google.common.hash.Funnel;
import work.metanet.api.log.ISystemLogService;
import work.metanet.api.statistical.IStatisticalService;
import work.metanet.constant.ConstCacheKey;
import work.metanet.constant.ConstSmsRequestType;
import work.metanet.util.SmsUtil;
import work.metanet.util.bloomFilter.BloomFilterHelper;
import work.metanet.util.bloomFilter.BloomFilterUtil;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.RandomUtil;

@SpringBootTest
class ApplicationTest {

	@Autowired SmsUtil SmsUtil;
	@Autowired ISystemLogService systemLogService;
	@Autowired IStatisticalService statisticalService;
	@Autowired StringRedisTemplate stringRedisTemplate;
	@Autowired BloomFilterUtil bloomFilterUtil;
	String key = "bb";
	
	@Test
	void bloomFilter_timeout() {
		String cacheKey = ConstCacheKey.BLOOM_FILTER.cacheKey("LOG");
		String rd = RandomUtil.randomString(5);
		bloomFilterUtil.add(cacheKey, rd, 10);
		Console.log("-------------{}",bloomFilterUtil.contains(cacheKey, rd));
	}
	
	@Test
	void bloomFilter() {
		String cacheKey = ConstCacheKey.BLOOM_FILTER.cacheKey("ORDER");
		
		//方法一
		BloomFilterHelper<String> bloomFilterHelper = new BloomFilterHelper<String>((Funnel<String>) (from, into) -> into.putString(from, Charsets.UTF_8).putString(from, Charsets.UTF_8),  9999999, 0.01);
		for (int i = 1; i <= 100; i++) {
			bloomFilterUtil.add(bloomFilterHelper, cacheKey, String.valueOf(i),-1);
		}
		System.out.println(bloomFilterUtil.contains(bloomFilterHelper, cacheKey, "100"));
		System.out.println(bloomFilterUtil.contains(bloomFilterHelper, cacheKey, "101"));
		
		//方法二
		bloomFilterUtil.add(cacheKey, "999",-1);
		System.out.println(bloomFilterUtil.contains(cacheKey, "999"));
		System.out.println(bloomFilterUtil.contains(cacheKey, "666"));
	}
	
	@Test
	void stringRedisTemplate() throws InterruptedException {
		int i = 1;
		while (true) {
			Thread.sleep(1000*1);
			stringRedisTemplate.opsForSet().add(key, String.valueOf(i));
			i ++;
		}
	}
	
	@Test
	void stringRedisTemplate2() throws InterruptedException {
		while (true) {
			Thread.sleep(500);
			System.out.println(stringRedisTemplate.opsForSet().pop(key));
		}
	}
	
	@Test
	void sendSms() {
		SmsUtil.sendSms(null,ConstSmsRequestType.REGISTER, "17603010900", "1234");
	}

}
