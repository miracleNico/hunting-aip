package work.metanet.interceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import work.metanet.api.user.IUserService;
import work.metanet.base.BaseController;
import work.metanet.base.ResultMessage;
import work.metanet.exception.LxException;
import work.metanet.utils.token.JwtUtil;

@Component
public class UserAuthInterceptor extends BaseController implements HandlerInterceptor{
	
	@DubboReference
	private IUserService userService;
	
    //该方法在action执行前执行，可以实现对数据的预处理，  
    //比如：编码、安全控制等。如果方法返回true，则继续执行action。
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取 HTTP HEAD 中的 TOKEN
        String authorization = request.getHeader("Authorization");
        if(StringUtils.isBlank(authorization)) 
        	throw LxException.of().setResult(ResultMessage.FAILURE_REQUEST_HEADER.result().setData("用户授权不能为空"));
        
        //UserToken user = getUser();
        Boolean checkJWT = JwtUtil.checkJWT(authorization);
        //Boolean hashKey = userService.hashUserTokenKey(user.getUserId());
        //String cacheUserToken = hashKey?userService.cacheUserToken(user.getUserId()):"";
        
        if (!checkJWT){
            throw LxException.of().setResult(ResultMessage.FAILURE_SESSION_TIMEOUT.result()).setMsg("授权已失效，请重新登录");
        }
        return checkJWT;
        
        /**使用新的用户认证机制再开放
        //token过期刷新
        if(!checkJWT && hashKey && authorization.equals(cacheUserToken)) {
        	UserToken newUser = new UserToken();
        	BeanUtil.copyProperties(user, newUser);
        	newUser.setRefresh(true);
        	String newUserToken = TokenUtil.generateUserToken(newUser);
        	response.setHeader("Authorization", newUserToken);
        	userService.cacheUserToken(user.getUserId(), newUserToken);
        	return true;
        }
        //认证成功
        if(checkJWT && hashKey && authorization.equals(cacheUserToken)) return true;
        //账号在其他设备登录
    	if(checkJWT && hashKey && !authorization.equals(cacheUserToken)) throw LxException.of().setResult(ResultMessage.FAILURE_AUTH_USER_LOGIN_OTHER.result());
    	
    	throw LxException.of().setResult(ResultMessage.FAILURE_AUTH_USER_OVERDUE.result());
    	*/
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