package work.metanet.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import work.metanet.common.security.annotation.EnableCustomConfig;
import work.metanet.common.security.annotation.EnableRyFeignClients;
import work.metanet.common.swagger.annotation.EnableCustomSwagger2;

/**
 * 定时任务
 * 
 * @author ruoyi
 */
@EnableCustomConfig
@EnableCustomSwagger2   
@EnableRyFeignClients
@SpringBootApplication
public class BaasJobApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(BaasJobApplication.class, args);
    }
}
