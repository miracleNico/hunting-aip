package work.metanet;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

import com.spring4all.mongodb.EnableMongoPlus;

import tk.mybatis.spring.annotation.MapperScan;

@EnableAsync
@EnableMongoPlus
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("work.metanet.server.dao")
public class DaasAdminApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder().web(WebApplicationType.NONE).sources(DaasAdminApplication.class).run(args);
	}

}
