package work.metanet.job;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import work.metanet.api.statistical.IStatisticalService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DisallowConcurrentExecution
public class JobUmengGetAllAppData extends QuartzJobBean {
	
	@DubboReference IStatisticalService statisticalService;
	
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException{
    	//JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
    	//ReqUmengGetDailyData req = (ReqUmengGetDailyData) jobDataMap.get("req");
    	try {
    		//log.info("-------job 保存-所有App统计数据-----");
			//statisticalService.saveGetAllAppData();
		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));;
		}
    }
}