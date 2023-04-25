package work.metanet.config;

import java.util.Arrays;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import work.metanet.interceptor.LogInterceptor;
import work.metanet.interceptor.SessionInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Resource
	private SessionInterceptor sessionInterceptor;
	@Autowired
	private LogInterceptor logInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(logInterceptor).addPathPatterns("/**");
		//自定义拦截器，添加拦截路径和排除拦截路径
		registry.addInterceptor(sessionInterceptor).addPathPatterns("/**").excludePathPatterns(Arrays.asList(
				"/","/favicon.ico","/error","/404","/500","/css/**","/images/**","/js/**","/json/**","/lib/**"
				,"/login/**","/channel/login"
				));
	}
	
	
	/**默认访问index.html
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController( "/" ).setViewName("index" );
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE );
        WebMvcConfigurer.super.addViewControllers(registry);
	}*/
	
	
	//参考https://www.cnblogs.com/anxminise/p/9808279.html
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("*")
		        .allowedOrigins("*")
		        .allowCredentials(true)
		        .allowedMethods("*")
		        .maxAge(3600);
	}
	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//解决 swagger-ui.html 404报错
        //registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        //解决 doc.html 404 报错
        //registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
	}
	
	
}