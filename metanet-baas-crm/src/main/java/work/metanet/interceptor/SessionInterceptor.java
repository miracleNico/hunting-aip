package work.metanet.interceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import work.metanet.api.admin.IAdminService;
import work.metanet.api.admin.protocol.ReqAdminLogin.RespAdminLogin;
import work.metanet.base.Result;
import work.metanet.base.ResultMessage;
import work.metanet.exception.LxException;
import work.metanet.utils.HttpServletRequestUtil;

@Component
public class SessionInterceptor implements HandlerInterceptor {
	
	@DubboReference
	private IAdminService adminService;
	
	private boolean sessionFailHandle(HttpServletRequest request,HttpServletResponse response,Result<Object> result) throws Exception {
		if(HttpServletRequestUtil.isAjax(request)) {
			response.setCharacterEncoding("UTF-8");
	        response.setContentType("application/json;charset=UTF-8");
			throw LxException.of().setResult(result);
		}
		request.setAttribute("msg", result.getMsg());
		response.sendRedirect("/");
		return false;
	}
	
    //该方法在action执行前执行，可以实现对数据的预处理，  
    //比如：编码、安全控制等。如果方法返回true，则继续执行action。
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object object = request.getSession().getAttribute("admin");
        if(object==null) {
        	//session过期
        	return sessionFailHandle(request, response, ResultMessage.FAILURE_SESSION_TIMEOUT.result());
        }
        
        RespAdminLogin admin = (RespAdminLogin) object;
        String sessionId = adminService.cacheSession(admin.getAdminId());
        
        if(!request.getSession().getId().equals(sessionId)) {
        	//账户已在其他地方登录
        	return sessionFailHandle(request, response, ResultMessage.FAILURE_LOGIN_OTHER.result());
        }
        
        adminService.cacheSession(admin.getAdminId(), HttpServletRequestUtil.getRequest().getSession().getId());
        
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