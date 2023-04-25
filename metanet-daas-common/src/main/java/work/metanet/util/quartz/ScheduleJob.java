package work.metanet.util.quartz;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor(staticName = "of")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Data
public class ScheduleJob implements Serializable{
	
	private static final long serialVersionUID = 7295022129467639970L;
	/**
     * 任务名称
     */
	@NonNull
    private String jobName;
    /**
     * 任务组名称
     */
    private String jobGroupName;
    
    /**
     * cron表达式
     */
    private String cron;
    /**
     * spring bean 任务类
     */
    private Class<? extends Job> jobClass;
    /**
     * 任务调用的方法传入的参数
     */
    private Map<Object,Object> jobDataMap;
    /**
     * 任务描述
     */
    private String jobDescription;
    /**
     * 是否要求唤醒
     */
    private boolean jobShouldRecover = false;
    /**
     * 是否持久化
     */
    private boolean jobDurability = false;
    
    public String getJobGroupName() {
    	if(StringUtils.isBlank(jobGroupName))
    		jobGroupName = this.getJobName();
		return jobGroupName;
	}
    
}