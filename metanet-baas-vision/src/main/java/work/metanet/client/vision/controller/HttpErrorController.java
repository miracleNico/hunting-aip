package work.metanet.client.vision.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import work.metanet.aop.ApiPermission;
import work.metanet.aop.ApiPermission.AUTH;
import work.metanet.base.Result;
import work.metanet.base.ResultMessage;
import work.metanet.utils.HttpServletRequestUtil;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @Description: 
 * @Author wanbo
 * @DateTime 2019/11/29
 */
@Slf4j
@ApiIgnore
@Controller
@RequestMapping("error")
public class HttpErrorController implements ErrorController{
    
	@Override
	public String getErrorPath() {
		return "error";
	}
	
	@ApiPermission(AUTH.OPEN)
	@RequestMapping
    public ModelAndView error(HttpServletRequest request,HttpServletResponse response) {
		HttpStatus httpStatus = HttpServletRequestUtil.getStatus(request);
		ModelAndView mv = new ModelAndView();
		Result<?> result = ResultMessage.FAILURE.result().setCode(httpStatus.value()).setMsg(httpStatus.getReasonPhrase()).setData(request.getRequestURI());
		mv.addAllObjects(BeanUtil.beanToMap(result));
    	
		String accept = ServletUtil.getHeader(request, "Accept", "utf-8");
		if(ServletUtil.isPostMethod(request) || (StringUtils.isNotBlank(accept) && accept.contains("application/json"))) {
			mv.setView(new MappingJackson2JsonView());
	    	log.info(JSONUtil.toJsonStr(result));
	    	return mv;
		}
		switch (httpStatus) {
			case NOT_FOUND:
				mv.setViewName(String.valueOf(httpStatus.value()));
				break;
			case INTERNAL_SERVER_ERROR:
				mv.setViewName(String.valueOf(httpStatus.value()));
				break;
			default:
				mv.setViewName("error");
				break;
		}
		return mv;
    	
    }

}