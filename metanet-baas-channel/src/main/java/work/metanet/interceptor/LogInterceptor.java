package work.metanet.interceptor;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import work.metanet.aop.ApiModule;
import work.metanet.aop.ApiOperLog;
import work.metanet.api.log.protocol.ReqSaveOperLog;
import work.metanet.base.BaseController;
import work.metanet.utils.HttpServletRequestUtil;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;

@Component
public class LogInterceptor extends BaseController implements HandlerInterceptor {
	
	@Value("${spring.application.name}")
    private String appName;
	
    //该方法在action执行前执行，可以实现对数据的预处理，  
    //比如：编码、安全控制等。如果方法返回true，则继续执行action。
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	if(!(handler instanceof HandlerMethod)) return true;
    	
    	Long currentTimeMillis = System.currentTimeMillis();
    	Method method = ((HandlerMethod) handler).getMethod();
		ApiModule apiModule = method.getDeclaringClass().getAnnotation(ApiModule.class);
		ApiOperLog apiOperLog = method.getAnnotation(ApiOperLog.class);
		
		String module = apiModule!=null?apiModule.module().getVal():null;
		String action = apiOperLog!=null?apiOperLog.action().getVal():null;
		String desc = apiOperLog!=null?apiOperLog.desc():null;
    	
    	ReqSaveOperLog operLog = ReqSaveOperLog.of(IdUtil.fastSimpleUUID())
    			.setAppName(appName)
		    	.setUri(request.getRequestURI())
		    	.setBody(JSONUtil.toJsonStr(ServletUtil.getBody(request)))
		    	.setParam(request.getQueryString())
		    	.setIsPost(ServletUtil.isPostMethod(request))
		    	.setOs(HttpServletRequestUtil.getOs(request))
		    	.setBrowser(HttpServletRequestUtil.getBrowser(request))
		    	.setIp(ServletUtil.getClientIP(request))
		    	.setUserId(getChannelId())
				.setUserName(getUsername())
				.setStartTime(currentTimeMillis)
				.setCreateTime(DateUtil.formatDateTime(DateUtil.date(currentTimeMillis)))
				.setModule(module).setAction(action).setDesc(desc)
		    	;
		    	
    	request.setAttribute("operLog", operLog);
        return true;
    }
    
    //该方法在action执行后，生成视图前执行。在这里，我们有机会修改视图层数据。  
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView  
            modelAndView) throws Exception {
        //System.out.println("action执行之后，生成视图之前执行！！");
    }  
  
    //最后执行，通常用于释放资源，处理异常。我们可以根据ex是否为空，来进行相关的异常处理。  
    //因为我们在平时处理异常时，都是从底层向上抛出异常，最后到了spring框架从而到了这个方法中。  
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {  
        //System.out.println("最后执行！！！一般用于释放资源！！");
    }  
}