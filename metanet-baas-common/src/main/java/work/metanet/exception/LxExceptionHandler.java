package work.metanet.exception;

import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import work.metanet.api.log.ISystemLogService;
import work.metanet.api.log.protocol.ReqSaveOperLog;
import work.metanet.base.Result;
import work.metanet.base.ResultMessage;
import work.metanet.utils.HttpServletRequestUtil;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.CryptoException;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;


/**
 * @Description: 全局异常处理
 * @Author wanbo
 * @DateTime 2019/11/29
 */
@Slf4j
@RestControllerAdvice
public class LxExceptionHandler {
	
	@DubboReference
	private ISystemLogService systemLogService;
    
    @ExceptionHandler(LxException.class)
    private ModelAndView exceptionHandler(HttpServletRequest request, HttpServletResponse response, LxException e) {
    	log.debug("@ExceptionHandler(LxException.class) 进入");
    	return checkModelAndView(request, response, e, e.getResult().setMsg(e.getMessage()));
    }
    
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    private ModelAndView exceptionHandler(HttpServletRequest request, HttpServletResponse response, SQLIntegrityConstraintViolationException e) {
    	log.debug("@ExceptionHandler(SQLIntegrityConstraintViolationException.class) 进入");
    	return checkModelAndView(request, response, e, ResultMessage.FAILURE_HANDLER_SERIOUS.result().setMsg("SQL执行异常"));
    }

    @ExceptionHandler(RpcException.class)
    private ModelAndView exceptionHandler(HttpServletRequest request, HttpServletResponse response, RpcException e) {
    	log.debug("@ExceptionHandler(RpcException.class) 进入" + e.getMessage());
    	return checkModelAndView(request, response, e, ResultMessage.FAILURE_HANDLER_SERIOUS.result().setMsg("服务维护中，请稍后再试！"));
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    private ModelAndView exceptionHandler(HttpServletRequest request, HttpServletResponse response, HttpMessageNotReadableException e) {
    	log.debug("@ExceptionHandler(HttpMessageNotReadableException.class) 进入");
    	return checkModelAndView(request, response, e, ResultMessage.FAILURE_HANDLER.result().setMsg("请求数据格式错误"));
    }
    
    @ExceptionHandler(CryptoException.class)
    private ModelAndView exceptionHandler(HttpServletRequest request, HttpServletResponse response, CryptoException e) {
    	log.debug("@ExceptionHandler(CryptoException.class) 进入");
    	return checkModelAndView(request, response, e, ResultMessage.FAILURE_HANDLER.result().setMsg("签名异常"));
    }
    
    @ExceptionHandler(SignatureException.class)
    private ModelAndView exceptionHandler(HttpServletRequest request, HttpServletResponse response, SignatureException e) {
    	log.debug("@ExceptionHandler(SignatureException.class) 进入");
    	return checkModelAndView(request, response, e, ResultMessage.FAILURE_HANDLER.result().setMsg("token解析错误"));
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    private ModelAndView exceptionHandler(HttpServletRequest request, HttpServletResponse response, IllegalArgumentException e) {
    	log.debug("@ExceptionHandler(IllegalArgumentException.class) 进入");
    	return checkModelAndView(request, response, e, ResultMessage.FAILURE_HANDLER.result().setMsg(e.getMessage()));
    }
    
    @ExceptionHandler(Exception.class)
    private ModelAndView exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
    	log.debug("@ExceptionHandler(Exception.class) 进入");
    	return checkModelAndView(request, response, e, ResultMessage.FAILURE.result().setMsg(e.getMessage()));
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ModelAndView exception(HttpServletRequest request, HttpServletResponse response,MethodArgumentNotValidException e) {
    	log.debug("@ExceptionHandler(MethodArgumentNotValidException.class) 进入");
        BindingResult bindingResult = e.getBindingResult();
        StringBuffer sb = new StringBuffer();
		for (ObjectError error : bindingResult.getAllErrors()) {
			sb.append(error.getDefaultMessage()).append(";");
		}
		if(sb.length()>0)sb.delete(sb.length()-1, sb.length());
		return checkModelAndView(request, response, e, ResultMessage.FAILURE_REQUEST_PARAM.result().setData(sb.toString()));
    }
    
    private ModelAndView checkModelAndView(HttpServletRequest request, HttpServletResponse response, Exception e,Result<?> result) {
    	log.debug("checkModelAndView 进入");

    	ReqSaveOperLog operLog = (ReqSaveOperLog) HttpServletRequestUtil.getRequest().getAttribute("operLog");
    	operLog.setTime(System.currentTimeMillis() - operLog.getStartTime());
    	
    	if(ResultMessage.FAILURE.getCode()==result.getCode()) {
    		operLog.setResp(ExceptionUtils.getStackTrace(e));
    	}else if(ResultMessage.FAILURE_HANDLER_SERIOUS.getCode()==result.getCode()) {
    		operLog.setResp(JSONUtil.toJsonStr(result));
    	}else {
    		operLog.setResp(JSONUtil.toJsonStr(result));
    	}
		
		try {
			log.error(JSONUtil.toJsonStr(operLog));
			//kafkaUtil.send(new ProducerRecord<String, String>(constant.kafka_topic_operLog, operLog.getTraceId(), JSONUtil.toJsonStr(operLog)));
			systemLogService.saveOperLog(operLog);
		} catch (Exception e1) {
			log.error("---操作日志保存异常---:{}",ExceptionUtils.getStackTrace(e1));
		}
		
		String accept = ServletUtil.getHeader(request, "Accept", "utf-8");
		if(ServletUtil.isPostMethod(request) || (StringUtils.isNotBlank(accept) && accept.contains("application/json"))) {
			//返回JSON
			return new ModelAndView(new MappingJackson2JsonView()).addAllObjects(BeanUtil.beanToMap(result));			
		}
		//返回页面
		return new ModelAndView("redirect:/error");
    	
    }
    
    
}