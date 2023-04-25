package work.metanet.aop.refresh;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import work.metanet.aop.LxRedisCache;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;

@Order(1)
@Aspect
@Component
public class AopRedisCache{
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Pointcut("@annotation(work.metanet.aop.LxRedisCache)")
    private void pointCut() {}
	
	@Before("pointCut()")
	public void doBefore() {
		
	}
	
	@Around("pointCut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
		Method method = signature.getMethod();
		LxRedisCache redisCache = method.getAnnotation(LxRedisCache.class);
		Type type = method.getGenericReturnType();
		Class<?> c = method.getReturnType();
		String cacheVal = stringRedisTemplate.opsForValue().get(redisCache.key().cacheKey());
		
		if(ClassUtil.equals(List.class, c.getName(), true)) {
			List<?> list = JSONUtil.toList(new JSONArray(cacheVal), ClassUtil.getTypeArgument(type.getClass()));
			if(CollUtil.isNotEmpty(list)) return list;
		}
		if(BeanUtil.isBean(c)) {
			Object object = JSONUtil.toBean(cacheVal, c);
			if (ObjectUtil.isNotNull(object)) return object;
		}
    	return proceedingJoinPoint.proceed();
    }
	
	@AfterReturning(pointcut = "pointCut()", returning = "resp")
    public void doAfterReturning(JoinPoint joinPoint, Object resp) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		LxRedisCache redisCache = method.getAnnotation(LxRedisCache.class);
		if (!stringRedisTemplate.hasKey(redisCache.key().cacheKey()) && ObjectUtil.isNotEmpty(resp)) {
			stringRedisTemplate.opsForValue().set(redisCache.key().cacheKey(), JSONUtil.toJsonStr(resp));			
		}
	}
	
	@AfterThrowing(pointcut = "pointCut()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
		
	}
	
	
}
