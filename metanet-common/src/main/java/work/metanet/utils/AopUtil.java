package work.metanet.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.aspectj.lang.JoinPoint;
import org.springframework.web.bind.annotation.RequestBody;

public class AopUtil {
	
	public final static Object getRequestBody(JoinPoint joinPoint) {
        Object target = joinPoint.getTarget();
        String methodName = joinPoint.getSignature().getName();
        Method method = null;
        for (Method m : target.getClass().getMethods()) {
            if (m.getName().equals(methodName)) {
                method = m;
                break;
            }
        }
        Parameter requestBodyParam = null;
        Parameter[]  parameters = method.getParameters();
        if (null != parameters) {
            for (Parameter parameter : parameters) {
                if (parameter.isAnnotationPresent(RequestBody.class)) {
                    requestBodyParam = parameter;
                    break;
                }
            }
        }
        Object[] args = joinPoint.getArgs();
        if (null != args && requestBodyParam!=null) {
            for (Object arg : args) {
                if (arg.getClass().equals(requestBodyParam.getType()) || requestBodyParam.getType().isAssignableFrom(arg.getClass())) {
                    return arg;
                }
            }
        }
        return null;
    }
	
}
