package work.metanet.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import de.codecentric.boot.admin.server.config.EnableAdminServer;

/**
 * 监控中心
 * 
 * @author ruoyi
 */
@EnableAdminServer
@SpringBootApplication
public class BaasMonitorApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(BaasMonitorApplication.class, args);
	}
}
