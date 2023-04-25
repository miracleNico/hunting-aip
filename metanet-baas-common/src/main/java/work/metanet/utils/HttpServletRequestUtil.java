package work.metanet.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * HttpServletRequest 工具类
 */
public class HttpServletRequestUtil {

  /**
   * 获取当前线程绑定的HttpServletRequest对象
   *
   * @return 当前线程绑定的HttpServletRequest对象
   */
  public static HttpServletRequest getRequest() {
    ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
    if (null == attributes) {
      throw new IllegalThreadStateException("no request bounded to current thread.");
    }
    return attributes.getRequest();
  }

  /**
   * 获取操作系统,浏览器及浏览器版本信息
   *
   * @param request HttpServletRequest对象
   * @return 客户端操作系统
   */
  public static String getOs(HttpServletRequest request) {
    String userAgent = request.getHeader("User-Agent").toLowerCase();
    String os = "";
    if (userAgent.indexOf("windows") >= 0) {
      os = "Windows";
    } else if (userAgent.indexOf("mac") >= 0) {
      os = "Mac";
    } else if (userAgent.indexOf("x11") >= 0) {
      os = "Unix";
    } else if (userAgent.indexOf("android") >= 0) {
      os = "Android";
    } else if (userAgent.indexOf("iphone") >= 0) {
      os = "IPhone";
    } else {
      os = "UnKnown, More-Info: " + userAgent;
    }
    return os;
  }

  /**
   * 获取操作系统,浏览器及浏览器版本信息
   *
   * @param request HttpServletRequest对象
   * @return 客户端浏览器
   */
  public static String getBrowser(HttpServletRequest request) {
    String userAgent = request.getHeader("User-Agent").toLowerCase();
    String browser = "";
    if (userAgent.contains("edge")) {
      browser = (userAgent.substring(userAgent.indexOf("edge")).split(" ")[0]).replace("/", "-");
    } else if (userAgent.contains("msie")) {
      String substring = userAgent.substring(userAgent.indexOf("msie")).split(";")[0];
      browser = substring.split(" ")[0].replace("msie", "IE") + "-" + substring.split(" ")[1];
    } else if (userAgent.contains("safari") && userAgent.contains("version")) {
      browser =
        (userAgent.substring(userAgent.indexOf("safari")).split(" ")[0]).split("/")[0] + "-" + (userAgent.substring(
          userAgent.indexOf("version")).split(" ")[0]).split("/")[1];
    } else if (userAgent.contains("opr") || userAgent.contains("opera")) {
      if (userAgent.contains("opera")) {
        browser =
          (userAgent.substring(userAgent.indexOf("opera")).split(" ")[0]).split("/")[0] + "-" + (userAgent.substring(
            userAgent.indexOf("version")).split(" ")[0]).split("/")[1];
      } else if (userAgent.contains("opr")) {
        browser = ((userAgent.substring(userAgent.indexOf("opr")).split(" ")[0]).replace("/", "-")).replace("opr",
          "Opera");
      }
    } else if (userAgent.contains("chrome")) {
      browser = (userAgent.substring(userAgent.indexOf("chrome")).split(" ")[0]).replace("/", "-");
    } else if ((userAgent.indexOf("mozilla/7.0") > -1) || (userAgent.indexOf("netscape6") != -1) || (
      userAgent.indexOf("mozilla/4.7") != -1) || (userAgent.indexOf("mozilla/4.78") != -1) || (
                 userAgent.indexOf("mozilla/4.08") != -1) || (userAgent.indexOf("mozilla/3") != -1)) {
      browser = "Netscape-?";
    } else if (userAgent.contains("firefox")) {
      browser = (userAgent.substring(userAgent.indexOf("firefox")).split(" ")[0]).replace("/", "-");
    } else if (userAgent.contains("rv")) {
      String IEVersion = (userAgent.substring(userAgent.indexOf("rv")).split(" ")[0]).replace("rv:", "-");
      browser = "IE" + IEVersion.substring(0, IEVersion.length() - 1);
    } else {
      browser = "UnKnown, More-Info: " + userAgent;
    }
    return browser;
  }

  /**
   * 判断请求类型是ajax还是表单
   *
   * @param request HttpServletRequest对象
   * @return 是否ajax请求
   */
  public static boolean isAjax(HttpServletRequest request) {
    String requestType = request.getHeader("X-Requested-With");
    return requestType != null;
  }

  /**
   * 获取请求的客户端IP地址（如使用了Nginx，需在Nginx做好相应的配置）
   *
   * @param request HttpServletRequest对象
   * @return 客户端IP地址
   */
  public static String getIpAddress(HttpServletRequest request) {
    String ip = request.getHeader("X-Forwarded-For");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_CLIENT_IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    return ip;
  }

  /**
   * 获取请求的资源标识
   *
   * @param request HttpServletRequest对象
   * @return 请求的资源标识
   */
  public static String getUri(HttpServletRequest request) {
    return request.getRequestURI();
  }
  
  public static HttpStatus getStatus(HttpServletRequest request) {
      Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
      if (statusCode != null) {
      	return HttpStatus.valueOf(statusCode);
      }
      return HttpStatus.INTERNAL_SERVER_ERROR;
  }
  
}
