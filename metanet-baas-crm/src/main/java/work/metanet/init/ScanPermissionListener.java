package work.metanet.init;

import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

//@Slf4j
//@Component
public class ScanPermissionListener implements ApplicationListener<ContextRefreshedEvent> {
	
	@Value("${spring.application.name}")
	private String appName;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event){
		// 根容器为Spring容器
		if (event.getApplicationContext().getParent() == null) {
			
			Map<String, Object> beans = event.getApplicationContext().getBeansWithAnnotation(Api.class);
			
			//List<ReqLoadingPermission> permissions = new ArrayList<ReqLoadingPermission>();
			
			for (Object bean : beans.values()) {
				if(bean!=null) {
					
					System.out.println("=========Bean==="+bean == null ? "null" : bean.getClass().getName());
					
					Api api = bean.getClass().getAnnotation(Api.class);
					RequestMapping requestMapping = bean.getClass().getAnnotation(RequestMapping.class);
					
					String moduleName = ArrayUtil.length(api.tags())>0?api.tags()[0]:"";
					String moduleUrl = ArrayUtil.length(requestMapping.value())>0?StrUtil.strip(requestMapping.value()[0],"/"):"";
					
					Method[] methods = bean.getClass().getDeclaredMethods();
					if(methods!=null) {
						for (Method method : methods) {
							
							PostMapping methodPostMapping = method.getAnnotation(PostMapping.class);
							GetMapping methodGetMapping = method.getAnnotation(GetMapping.class);
							RequestMapping methodRequestMapping = method.getAnnotation(RequestMapping.class);
							
							String methodUrl = "";
							
							if(!BeanUtil.isEmpty(methodPostMapping) && !ArrayUtil.isEmpty(methodPostMapping.value()))
								methodUrl = StrUtil.strip(methodPostMapping.value()[0],"/");
							if(!BeanUtil.isEmpty(methodGetMapping) && !ArrayUtil.isEmpty(methodGetMapping.value()))
								methodUrl = StrUtil.strip(methodGetMapping.value()[0],"/");
							if(!BeanUtil.isEmpty(methodRequestMapping) && !ArrayUtil.isEmpty(methodRequestMapping.value()))
								methodUrl = StrUtil.strip(methodRequestMapping.value()[0],"/");
							
							if(StringUtils.isEmpty(methodUrl))
								return;
							
							ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
							String methodName = StrUtil.isEmpty(apiOperation.value())?"":apiOperation.value();
							
							String permissionName = moduleName+"."+methodName; 
							String permissionUrl = "/"+moduleUrl+"/"+methodUrl;
							
							//permissions.add(new ReqLoadingPermission().setName(permissionName).setUrl(permissionUrl));
						}
						
					}
					
				}
				
			}
			/**
			if(permissions.size()>0)
				try {
					//stringRedisTemplate.opsForValue().set(ConstRedisCache.RESOURCE.cacheKey(appName.toUpperCase()),JSONUtil.toJsonStr(permissions));
				} catch (Exception e) {
					log.error(ExceptionUtils.getStackTrace(e));
				}
			*/
			//System.out.println("=====ContextRefreshedEvent=====" + event.getSource().getClass().getName());
		}
	}
}