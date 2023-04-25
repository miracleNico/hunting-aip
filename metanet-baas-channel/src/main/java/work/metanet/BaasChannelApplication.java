package work.metanet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

//@EnableRedisHttpSession//session负载均衡
@EnableAsync
@EnableDiscoveryClient
@SpringBootApplication
/**
 * 如果加载了数据源依赖,属性文件未配置的情况下需要排除数据源加载
 * (exclude= {
		DruidDataSourceAutoConfigure.class,DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class})*/
public class BaasChannelApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(BaasChannelApplication.class, args);
	}
	
}
