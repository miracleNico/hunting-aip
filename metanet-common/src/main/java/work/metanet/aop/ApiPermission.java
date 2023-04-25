package work.metanet.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 权限
 * @Author wanbo
 * @DateTime 2019/11/13
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiPermission {
	
	AUTH value() default AUTH.AUTH;
	
	@Getter
	@AllArgsConstructor
	enum AUTH {
		AUTH,
		OPEN
		;
	}
	
}
