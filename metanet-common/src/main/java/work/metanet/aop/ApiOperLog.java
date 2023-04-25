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
public @interface ApiOperLog {
	
	/**
	 * @Description: 类型
	 * @Author wanbo
	 * @DateTime 2019/11/13
	 */
	ACTION action() default ACTION.NULL;
	
	/**
	 * @Description: 描述
	 * @Author wanbo
	 * @DateTime 2019/11/13
	 */
	String desc() default "";
	
	@Getter
	@AllArgsConstructor
	enum ACTION {
		NULL(null),INFO("详情"),ADD("添加"), UPDATE("更新"), SAVE("保存"), DELETE("删除"), SELECT("查询"), UPLOAD("上传"), DOWNLOAD("下载"), LOGIN("登录"), LOGOUT("登出"),
		SEND_CODE("发送验证码"),UPDATE_PWD("修改密码"), RESET_PWD("重置密码"), IMPORT("导入"),OTHER("其它");
		String val;
	}

	
}
