package work.metanet.util.bloomFilter;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;

@Component
public class BloomFilterUtil {
	
	@Autowired 
	private StringRedisTemplate stringRedisTemplate;
	@Autowired 
	private BloomFilterHelper<String> bloomFilterHelper;
	
	/**
	 * 根据给定的布隆过滤器添加值
	 */
	public void add(String key, String value,long timeoutSeconds) {
		add(bloomFilterHelper, key, value, timeoutSeconds);
	}
	
	/**
	 * 根据给定的布隆过滤器判断值是否存在
	 */
	public boolean contains(String key, String value) {
		return contains(bloomFilterHelper, key, value);
	}
	
	/**
	 * 根据给定的布隆过滤器添加值
	 */
	public <T> void add(BloomFilterHelper<T> bloomFilterHelper, String key, T value, long timeout) {
	    Preconditions.checkArgument(bloomFilterHelper != null, "bloomFilterHelper不能为空");
	    int[] offset = bloomFilterHelper.murmurHashOffset(value);
	    for (int i : offset) {
	    	stringRedisTemplate.opsForValue().setBit(key, i, true);
	    	if(timeout>=0) {
	    		stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
	    	}
	    }
	}
	
	/**
	 * 根据给定的布隆过滤器判断值是否存在
	 */
	public <T> boolean contains(BloomFilterHelper<T> bloomFilterHelper, String key, T value) {
	    Preconditions.checkArgument(bloomFilterHelper != null, "bloomFilterHelper不能为空");
	    int[] offset = bloomFilterHelper.murmurHashOffset(value);
	    for (int i : offset) {
	        if (!stringRedisTemplate.opsForValue().getBit(key, i)) {
	            return false;
	        }
	    }
	    return true;
	}
}
