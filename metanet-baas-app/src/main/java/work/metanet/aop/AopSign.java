package work.metanet.aop;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import work.metanet.api.app.IAppService;
import work.metanet.api.app.vo.AppKeyVo;
import work.metanet.base.BaseController;
import work.metanet.base.ResultMessage;
import work.metanet.exception.LxException;
import work.metanet.utils.HttpServletRequestUtil;
import work.metanet.utils.LxSignUtil;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;

//@Slf4j
@Order(1)
//@Aspect
@Component
public class AopSign extends BaseController{
	
	@DubboReference IAppService appService;
	
	@Pointcut("@annotation(io.swagger.annotations.ApiOperation)")
    private void pointCut() {}
	
	@Before("pointCut()")
	public void doBefore() {
		
	}
	
	@Around("pointCut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		HttpServletRequest request = HttpServletRequestUtil.getRequest();
		String appId = request.getHeader("appId");
        String sign = request.getHeader("sign");
        String timestamp = request.getHeader("timestamp");
    	String nonce = request.getHeader("nonce");
        
        if(StringUtils.isBlank(appId)) throw LxException.of().setResult(ResultMessage.FAILURE_REQUEST_HEADER.result().setData("appId不能为空"));
        
        if(StringUtils.isBlank(sign)) throw LxException.of().setResult(ResultMessage.FAILURE_REQUEST_HEADER.result().setData("sign不能为空"));
        
        if(StringUtils.isBlank(timestamp)) throw LxException.of().setResult(ResultMessage.FAILURE_REQUEST_HEADER.result().setData("timestamp不能为空"));
        
        if(StringUtils.isBlank(nonce)) throw LxException.of().setResult(ResultMessage.FAILURE_REQUEST_HEADER.result().setData("nonce不能为空"));
        
        AppKeyVo appKeyVo = appService.appKey(appId);
        
        if(BeanUtil.isEmpty(appKeyVo)) throw LxException.of().setResult(ResultMessage.FAILURE_AUTH_APPID.result());
        
        String body = ServletUtil.getBody(request);
        Map<String, Object> map = MapUtil.newHashMap();
        if(JSONUtil.isJsonObj(body)) {
        	map = JSONUtil.parseObj(body);
        }
        if(JSONUtil.isJsonArray(body)) {
        	map.put("", body);
        }
        map.put("appKey", appKeyVo.getAppKey());
        map.put("timestamp", timestamp);
        map.put("nonce", nonce);
        System.out.println(LxSignUtil.sign_Sha256withAES(appKeyVo.getAppSecret(), map));
        
        Boolean eqSign = StrUtil.equals(LxSignUtil.getDigestStr_sha256(map), LxSignUtil.unSign_AES(appKeyVo.getAppSecret(),sign));
        
        if(!eqSign) throw LxException.of().setResult(ResultMessage.FAILURE_AUTH_SIGN.result());
        
        if(DateUtil.currentSeconds()-Convert.toLong(timestamp)>60) throw LxException.of().setResult(ResultMessage.FAILURE_AUTH_TIMESTAMP.result());
        
    	return proceedingJoinPoint.proceed();
    }
	
	@AfterReturning(pointcut = "pointCut()", returning = "resp")
    public void doAfterReturning(JoinPoint joinPoint, Object resp) {
		
	}
	
	@AfterThrowing(pointcut = "pointCut()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
		
	}
	
	
}
