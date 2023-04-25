package work.metanet.vision;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

import com.spring4all.mongodb.EnableMongoPlus;

import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackages = {"work.metanet","work.metanet"})
@EnableAsync
@EnableMongoPlus
@EnableDiscoveryClient
@MapperScan("work.metanet.vision.dao")
@EnableAutoDataSourceProxy
public class MetanetDaasVisionApplication {

	public static void main(String[] args) {
		SpringApplication.run(MetanetDaasVisionApplication.class, args);
	}

}
