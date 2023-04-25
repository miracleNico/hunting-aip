package work.metanet.util.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;

import work.metanet.constant.ConstCacheKey;

@Configuration
public class RedisLockConfiguration {
    
	@Bean
    public RedisLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
        return new RedisLockRegistry(redisConnectionFactory, ConstCacheKey.LOCK.name(), TimeUnit.SECONDS.toMillis(ConstCacheKey.LOCK.getExpire()));
    }
    
}