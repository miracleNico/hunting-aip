package work.metanet.aop;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import work.metanet.aop.ApiPermission.AUTH;
import work.metanet.api.admin.protocol.ReqAdminLogin.RespAdminLogin;
import work.metanet.base.BaseController;
import work.metanet.base.ResultMessage;
import work.metanet.constant.Constant;
import work.metanet.exception.LxException;
import work.metanet.utils.HttpServletRequestUtil;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;

@Aspect
@Order(1)
@Component
public class AopPermission extends BaseController{
	
	@Autowired
	private Constant constant;
	
	@Value("${spring.application.name}")
    private String appName;
	
	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    private void pointCut() {}
	
	@Before("pointCut()")
	public void doBefore() {
		
	}
	
	@Around("pointCut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
		Method method = signature.getMethod();
		//RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
		ApiPermission apiPermission = method.getAnnotation(ApiPermission.class);
		HttpServletRequest request = HttpServletRequestUtil.getRequest();
		
		//System.out.println(request.getRequestURI());
		
		if(BeanUtil.isNotEmpty(apiPermission) && apiPermission.value()==AUTH.OPEN) {
			return proceedingJoinPoint.proceed();
		}
		
		RespAdminLogin admin = (RespAdminLogin) request.getSession().getAttribute("admin");
		
		if(admin!=null && constant.getAdministrator_admin_id().equals(admin.getAdminId())) {
			return proceedingJoinPoint.proceed();
		}
		if(admin!=null && ArrayUtil.contains(StrUtil.splitToArray(admin.getAdminPermissionApis(), ','), request.getRequestURI())) {
			return proceedingJoinPoint.proceed();
		}
		
		ResultMessage resultMessage = ResultMessage.FAILURE_AUTH_PERMISSION;
		String accept = ServletUtil.getHeader(request, "Accept", "utf-8");
    	if(StringUtils.isNotBlank(accept) && accept.contains("application/json")) {
    		throw LxException.of().setResult(resultMessage.result());
    	}else {
    		return String.valueOf(resultMessage.getCode());
    	}
		
    }
	
	@AfterReturning(pointcut = "pointCut()", returning = "resp")
    public void doAfterReturning(JoinPoint joinPoint, Object resp) {
		
	}
	
	@AfterThrowing(pointcut = "pointCut()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
		
	}
	
}
