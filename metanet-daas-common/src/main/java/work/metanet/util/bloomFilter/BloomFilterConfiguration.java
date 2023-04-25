package work.metanet.util.bloomFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Charsets;
import com.google.common.hash.Funnel;

@Configuration
public class BloomFilterConfiguration {
	
	@Bean
	public BloomFilterHelper<String> BloomFilterHelper() {
    	return new BloomFilterHelper<String>((Funnel<String>) (from, into) -> into.putString(from, Charsets.UTF_8).putString(from, Charsets.UTF_8),  9999999, 0.01);
	}
    
}