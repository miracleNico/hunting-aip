package work.metanet.aop;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import work.metanet.api.log.ISystemLogService;
import work.metanet.api.log.protocol.ReqSaveOperLog;
import work.metanet.utils.HttpServletRequestUtil;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class AopOperLog{
	
	@DubboReference
	private ISystemLogService systemLogService;
	
	//@Pointcut("execution(public * work.metanet.controller.*.*(..))")
	@Pointcut("@annotation(io.swagger.annotations.ApiOperation)")
    private void pointCut() {}
	
	@Before("pointCut()")
	public void doBefore() {
		
	}
	
	@Around("pointCut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    	return proceedingJoinPoint.proceed();
    }
	
	@AfterReturning(pointcut = "pointCut()", returning = "resp")
    public void doAfterReturning(JoinPoint joinPoint, Object resp) {
		try {
			ReqSaveOperLog operLog = (ReqSaveOperLog) HttpServletRequestUtil.getRequest().getAttribute("operLog");
			if(BeanUtil.isEmpty(operLog)) return;
			operLog.setTime(System.currentTimeMillis() - operLog.getStartTime());
			operLog.setResp(JSONUtil.toJsonStr(resp));
			log.info(JSONUtil.toJsonStr(operLog));
			//kafkaUtil.send(new ProducerRecord<String, String>(constant.kafka_topic_operLog, operLog.getTraceId(), JSONUtil.toJsonStr(operLog)));
			systemLogService.saveOperLog(operLog);
		} catch (Exception e) {
			log.error("---操作日志保存异常---:{}",ExceptionUtils.getStackTrace(e));
		}
	}
	
	@AfterThrowing(pointcut = "pointCut()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
		
	}
	
	
}
