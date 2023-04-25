package work.metanet.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public enum ResultMessage {
	
	SUCCESS(200,"操作成功",true),
	FAILURE(500,"服务异常",false),
	FAILURE_HANDLER(501,"全局异常处理",false),
	FAILURE_HANDLER_SERIOUS(502,"全局异常处理-严重",false),
	FAILURE_CUSTOM(1000,"自定义异常",false),
	FAILURE_DEVICE_NOT_ACTIVATE(1001,"设备未激活",false),
	FAILURE_DEVICE_DISABLE(1002,"设备已禁用",false),
	FAILURE_TIP_NOT_UPGRADE(1003,"暂无升级",false),
	FAILURE_AUTH_APPID(1004,"appId无效",false),
	FAILURE_AUTH_SIGN(1005,"非法篡改签名",false),
	FAILURE_AUTH_TIMESTAMP(1006,"请求已过期",false),
	FAILURE_AUTH_SIGN_SECRET(1007,"无效的签名",false),
	FAILURE_AUTH_USER_OVERDUE(1008,"登录已失效，请重新登录",false),
	FAILURE_AUTH_USER_LOGIN_OTHER_CLIENT(1009,"账号在其他设备登录",false),
	FAILURE_AUTH_PERMISSION(1010,"无权访问",false),
	FAILURE_SESSION_TIMEOUT(1080,"会话已过期",false),
	FAILURE_LOGIN_OTHER(1081,"账号在其他地方登陆",false),
	FAILURE_REQUEST_PARAM(1082,"请求参数错误",false),
	FAILURE_REQUEST_HEADER(1083,"请求头信息错误",false),
	FAILURE_RECOGNITION_EMPTY(1100,"未识别到目标",false),
	
	FAILURE_OPENAPI_AUTH(2000,"token无效",false),
	FAILURE_OPENAPI_AUTH_24H_MAX_NUMBER(2001,"设备认证次数过多",false),
	FAILURE_OPENAPI_AUTH_SIGN_MAX_NUMBER(2002,"签约设备数已到达上限",false),
	FAILURE_OPENAPI_AUTH_OTHER_CLIENT(2003,"相同设备已在另一端认证",false),
	FAILURE_OPENAPI_AUTH_REFRESH_TOKEN(2004,"请使用刷新后的token",false)
	;
	
	private int code;
	private String msg;
	private boolean success;
	
	public <T> Result<T> result(){
		return new Result<T>(success,code,msg,null);
	}
	
	public <T> Result<T> result(T t){ 
		return new Result<T>(success,code,msg,t);
	}
	
	
}
