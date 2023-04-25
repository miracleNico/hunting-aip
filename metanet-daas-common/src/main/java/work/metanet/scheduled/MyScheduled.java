package work.metanet.scheduled;

import org.springframework.scheduling.annotation.Scheduled;

//@Component
public class MyScheduled {
	
	
	@Scheduled(fixedRate = 5000)
	private void refreshMapper() {
		
	}

}
