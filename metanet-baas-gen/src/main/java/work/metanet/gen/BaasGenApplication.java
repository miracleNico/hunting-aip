package work.metanet.gen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import work.metanet.common.security.annotation.EnableCustomConfig;
import work.metanet.common.security.annotation.EnableRyFeignClients;
import work.metanet.common.swagger.annotation.EnableCustomSwagger2;

/**
 * 代码生成
 * 
 * @author ruoyi
 */
@EnableCustomConfig
@EnableCustomSwagger2   
@EnableRyFeignClients
@SpringBootApplication
public class BaasGenApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(BaasGenApplication.class, args);
    }
}
