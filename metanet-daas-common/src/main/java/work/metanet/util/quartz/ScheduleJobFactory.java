package work.metanet.util.quartz;

import java.util.Map;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class ScheduleJobFactory {
	
	@Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    
    /**
     * @Description: 执行一个任务
     * @Author wanbo
     * @DateTime 2019/12/26
     */
    public void exec(ScheduleJob scheduleJob) throws SchedulerException {
    	removeJob(scheduleJob.getJobName(),scheduleJob.getJobGroupName());
    	addJob(scheduleJob);
    }
    
    
    /**
     * @Description: 添加任务
     * @Author wanbo
     * @DateTime 2019/12/25
     */
    private void addJob(ScheduleJob scheduleJob) throws SchedulerException {
    	Scheduler scheduler = schedulerFactoryBean.getScheduler();
    	String jobName = scheduleJob.getJobName();
    	String jobGroupName = scheduleJob.getJobGroupName();
    	String triggerName = scheduleJob.getJobName();
    	String triggerGroupName = scheduleJob.getJobGroupName();
    	Class<? extends Job> jobClass = scheduleJob.getJobClass();
    	String cron = scheduleJob.getCron();
    	String jobDescription = scheduleJob.getJobDescription();
    	boolean jobDurability = scheduleJob.isJobDurability();
    	boolean jobShouldRecover = scheduleJob.isJobShouldRecover();
    	Map<Object, Object> datamap = scheduleJob.getJobDataMap();
    	//任务设置
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
        		.withIdentity(jobName, jobGroupName)
        		.withDescription(jobDescription)
        		.storeDurably(jobDurability)
        		.requestRecovery(jobShouldRecover)
        		.build();
        
        if (datamap != null) {
            for (Object o : datamap.keySet()) {
                jobDetail.getJobDataMap().put(o.toString(), datamap.get(o));
            }
        }
        //触发器设置
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroupName).withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
        //设置调度器
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }
    
    
    /**
     * @Description: 重置触发器
     * @Author wanbo
     * @DateTime 2019/12/25
     */
    public void resetTrigger(String name, String groupName, String cron) throws SchedulerException {
    	Scheduler scheduler = schedulerFactoryBean.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(name, groupName);
        CronTrigger cronTrigger = (CronTrigger)scheduler.getTrigger(triggerKey);
    	if (!cronTrigger.getCronExpression().equalsIgnoreCase(cron)) {
            //重置触发器
    		cronTrigger = cronTrigger.getTriggerBuilder()
    				.withIdentity(triggerKey)
    				.withSchedule(CronScheduleBuilder.cronSchedule(cron))
    				.build();
            //重置调度器
            scheduler.rescheduleJob(triggerKey, cronTrigger);
        }
    }
    
    /**
     * @Description: 重置触发器
     * @Author wanbo
     * @DateTime 2019/12/25
     */
    public void resetTrigger(String name, String cron) throws SchedulerException {
    	resetTrigger(name, cron);
    }
    
    /**
     * @Description: 移除一个任务
     * @Author wanbo
     * @DateTime 2019/12/25
     */
    public void removeJob(String name, String groupName) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(name, groupName);
        //停止触发器
        scheduler.pauseTrigger(triggerKey);
        //移除触发器
        scheduler.unscheduleJob(triggerKey);
        //删除任务
        scheduler.deleteJob(JobKey.jobKey(name, groupName));
    }
    
    /**
     * @Description: 移除一个任务
     * @Author wanbo
     * @DateTime 2019/12/25
     */
    public void removeJob(String name) throws SchedulerException {
    	removeJob(name, name);
    }
    
    /**
     * @Description: 暂停指定任务
     * @Author wanbo
     * @DateTime 2019/12/25
     */
    public void pauseJob(String name, String groupName) throws SchedulerException {
        schedulerFactoryBean.getScheduler().pauseJob(JobKey.jobKey(name, groupName));
    }
    
    /**
     * @Description: 暂停指定任务
     * @Author wanbo
     * @DateTime 2019/12/25
     */
    public void pauseJob(String name) throws SchedulerException {
        schedulerFactoryBean.getScheduler().pauseJob(JobKey.jobKey(name, name));
    }
    
    /**
     * @Description: 恢复指定任务
     * @Author wanbo
     * @DateTime 2019/12/25
     */
    public void resumeJob(String name, String groupName) throws SchedulerException {
        schedulerFactoryBean.getScheduler().resumeJob(JobKey.jobKey(name, groupName));
    }
    
    /**
     * @Description: 恢复指定任务
     * @Author wanbo
     * @DateTime 2019/12/25
     */
    public void resumeJob(String name) throws SchedulerException {
        schedulerFactoryBean.getScheduler().resumeJob(JobKey.jobKey(name, name));
    }
    
    /**
     * @Description: 暂停所有任务
     * @Author wanbo
     * @DateTime 2019/12/25
     */
    public void pauseAll() throws SchedulerException {
        schedulerFactoryBean.getScheduler().pauseAll();
    }
    
    /**
     * @Description: 恢复所有任务
     * @Author wanbo
     * @DateTime 2019/12/25
     */
    public void resumeAll() throws SchedulerException {
        schedulerFactoryBean.getScheduler().resumeAll();
    }
    
    /**
     * @Description: 关闭所有任务
     * @Author wanbo
     * @DateTime 2019/12/25
     */
    public void shutdownJobs() throws SchedulerException {
        schedulerFactoryBean.getScheduler().shutdown();
    }
    
    /**
     * @Description: 启动所有任务
     * @Author wanbo
     * @DateTime 2019/12/25
     */
    public void startJobs() throws SchedulerException {
        schedulerFactoryBean.getScheduler().start();
    }

}