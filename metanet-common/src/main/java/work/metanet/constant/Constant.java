package work.metanet.constant;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@RefreshScope
public class Constant {
	
	@Value("${lx.custom.kafka.topic.operLog:}")
	private String kafka_topic_operLog;
	
	@Value("${lx.custom.sms.business.snCode.maxFailCount:3}")
	private Integer maxFailCount;
	
	@Value("${lx.custom.sms.warning.phone:}")
	private String adminPhone;
	
	private final String default_password = "e10adc3949ba59abbe56e057f20f883e";
	
	private final String administrator_admin_id = "0";
	
	private final String company = "METANET";
	
	private final String random_base_number = "0123456789";
	
	@Value("${lx.custom.sign.enable:}")
	private Boolean signEnable;
	@Value("${metanet.custom.ymsj.sign.secret:}")
	private String ymsjSignSecret;
	@Value("${metanet.custom.ymsj.domain:}")
	private String ymsjDomain;
	@Value("${metanet.custom.ymsj.package:}")
	private String ymsjPackage;
	
	@Value("${lx.custom.crm.menu.path:}")
	private String crm_menu_path;
	@Value("${lx.custom.channel.menu.path:}")
	private String channel_menu_path;
	
	//积分兑换比率(10分钟=1积分)
	@Value("${metanet.custom.score.learningMinutes_convert_score:10}")
	private Long learningMinutes_convert_score;
	
	//日累计积分上限
	@Value("${metanet.custom.score.day_max_convert_score:20}")
	private BigDecimal day_max_convert_score;
	
	//开放平台24小时内同一台设备应用最多认证2次
	@Value("${lx.custom.openapi.device.24h.max.auth.number:2}")
	private Integer openapi_device_24h_max_auth_number;
	//开放平台服务端token缓存时长(用于长期未操作需要重新认证)
	@Value("${lx.custom.openapi.token.server.cache.timeout.day:30}")
	private Long openapi_token_server_cache_timeout_day;
	//开放平台签名开关
	@Value("${lx.custom.openapi.sign.enable:true}")
	private Boolean openapi_sign_enable;
	
	@Value("${lx.custom.redis.lock.timeout.seconds:60}")
	private Long redis_lock_timeout_seconds;
	
}
