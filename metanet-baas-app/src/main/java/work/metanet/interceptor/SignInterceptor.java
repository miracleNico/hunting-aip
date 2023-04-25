package work.metanet.interceptor;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import work.metanet.api.app.IAppService;
import work.metanet.api.app.vo.AppKeyVo;
import work.metanet.base.BaseController;
import work.metanet.base.ResultMessage;
import work.metanet.constant.Constant;
import work.metanet.exception.LxException;
import work.metanet.utils.LxSignUtil;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;

@Component
public class SignInterceptor extends BaseController implements HandlerInterceptor{
	
	@DubboReference
	private IAppService appService;
	@Autowired
	private Constant constant;
	
    //该方法在action执行前执行，可以实现对数据的预处理，  
    //比如：编码、安全控制等。如果方法返回true，则继续执行action。
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	if(!constant.getSignEnable()) return true;
    	
    	String appId = request.getHeader("appId");
    	String cid = request.getHeader("cid");
        String sign = request.getHeader("sign");
        String timestamp = request.getHeader("timestamp");
    	String nonce = request.getHeader("nonce");
        
        if(StringUtils.isBlank(appId)) 
        	throw LxException.of().setResult(ResultMessage.FAILURE_REQUEST_HEADER.result().setData("应用不能为空"));
        
        if(StringUtils.isBlank(sign)) 
        	throw LxException.of().setResult(ResultMessage.FAILURE_REQUEST_HEADER.result().setData("签名不能为空"));
        
        if(StringUtils.isBlank(timestamp)) 
        	throw LxException.of().setResult(ResultMessage.FAILURE_REQUEST_HEADER.result().setData("时间戳不能为空"));
        
        if(StringUtils.isBlank(nonce)) 
        	throw LxException.of().setResult(ResultMessage.FAILURE_REQUEST_HEADER.result().setData("随机数不能为空"));
        
        AppKeyVo appKeyVo = appService.appKey(appId);
        if(BeanUtil.isEmpty(appKeyVo)) 
        	throw LxException.of().setResult(ResultMessage.FAILURE_AUTH_APPID.result());
        
        String body = ServletUtil.getBody(request);
        Map<String, Object> map = MapUtil.newHashMap();
        if(JSONUtil.isJsonObj(body)) {
        	map = Convert.toMap(String.class, Object.class, JSONUtil.parseObj(body));
        }
        if(JSONUtil.isJsonArray(body)) {
        	//map.put("", body);
        }
        map.put("appKey", appKeyVo.getAppKey());
        map.put("cid", cid);
        map.put("timestamp", timestamp);
        map.put("nonce", nonce);
        //System.out.println(LxSignUtil.sign_Sha256withAES(appKeyVo.getAppSecret(), map));
        if(!StrUtil.equals(LxSignUtil.getDigestStr_sha256(map), LxSignUtil.unSign_AES(appKeyVo.getAppSecret(),sign)))
        	throw LxException.of().setResult(ResultMessage.FAILURE_AUTH_SIGN.result());
        
        if(DateUtil.currentSeconds()-Convert.toLong(timestamp)>60)
        	throw LxException.of().setResult(ResultMessage.FAILURE_AUTH_TIMESTAMP.result());
        
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