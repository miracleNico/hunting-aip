package work.metanet.interceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import work.metanet.api.deviceApp.IDeviceAppService;
import work.metanet.base.ResultMessage;
import work.metanet.exception.LxException;

@Component
public class DeviceAuthInterceptor implements HandlerInterceptor {
	
	@DubboReference
	private IDeviceAppService deviceAppService;
	
    //该方法在action执行前执行，可以实现对数据的预处理，  
    //比如：编码、安全控制等。如果方法返回true，则继续执行action。
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	String cid = request.getHeader("cid");
    	String packageName = request.getHeader("packageName");
    	
    	//由于历史原因 开放知了的高木学习产品
    	if("com.ailexi.gaomuxuexi".equals(packageName)) return true;
    	
        if(StringUtils.isBlank(cid)) 
        	throw LxException.of().setResult(ResultMessage.FAILURE_REQUEST_HEADER.result().setData("cid不能为空"));
        
        if(!deviceAppService.isActivate(cid))
        	throw LxException.of().setResult(ResultMessage.FAILURE_DEVICE_NOT_ACTIVATE.result());
        
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