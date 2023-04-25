package work.metanet.init;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1) //执行顺序,优先级越小优先级越高
public class Init implements ApplicationRunner {
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		
	}
	
}