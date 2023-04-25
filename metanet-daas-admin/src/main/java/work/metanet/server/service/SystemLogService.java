package work.metanet.server.service;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.scheduling.annotation.Async;

import work.metanet.api.log.ISystemLogService;
import work.metanet.api.log.protocol.ReqOperLogList;
import work.metanet.api.log.protocol.ReqOperLogList.RespOperLogList;
import work.metanet.api.log.protocol.ReqSaveOperLog;
import work.metanet.base.RespPaging;
import work.metanet.constant.ConstCacheKey;
import work.metanet.constant.Constant;
import work.metanet.exception.DaasException;
import work.metanet.model.StAppActive;
import work.metanet.model.mongo.OperLog;
import work.metanet.server.dao.StAppActiveMapper;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.PageUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DubboService
public class SystemLogService implements ISystemLogService{

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private StAppActiveMapper stAppActiveMapper;
	@Autowired
    private RedisLockRegistry redisLockRegistry;
	@Autowired
	private Constant constant;
	
	/**
	//@KafkaListener(topics = {"#{'${lx.custom.kafka.topic.clientApp.operLog}'}","#{'${lx.custom.kafka.topic.clientCrm.operLog}'}","#{'${lx.custom.kafka.topic.clientChannel.operLog}'}"})
	@KafkaListener(topicPattern = "kafka.topic.operLog.*")
	public void saveOperLog(ConsumerRecord<String, String> record) throws Exception{
		OperLog operLog = JSONUtil.toBean(record.value(), OperLog.class);
		mongoTemplate.save(operLog);
		if(StrUtil.contains(record.topic(), "app") && StrUtil.isNotBlank(operLog.getUserId())) {
			userLogMapper.insert(new UserLog(operLog.getTraceId(), operLog.getUserId(), operLog.getUri(), operLog.getCreateTime()));
		}
	}
	*/
	
	@Async
	@Override
	public void saveOperLog(ReqSaveOperLog req) throws Exception{
		mongoTemplate.save(BeanUtil.copyProperties(req, OperLog.class));
		String appId = req.getAppId();
		String userId = req.getUserId();
		if(StrUtil.isNotBlank(appId) && StrUtil.isNotBlank(userId)) {
			String date = DateUtil.format(DateUtil.parse(req.getCreateTime()), "yyyyMMdd");
			String cacheKey = ConstCacheKey.ST_DAY_ACTIVE.cacheKey(date,appId,userId);
			if(!stringRedisTemplate.hasKey(cacheKey)){
				stringRedisTemplate.opsForValue().set(cacheKey, "1", Duration.ofSeconds(ConstCacheKey.ST_DAY_ACTIVE.getExpire()));
				Lock lock = redisLockRegistry.obtain(appId);
				if(lock.tryLock(constant.getRedis_lock_timeout_seconds(),TimeUnit.SECONDS)) {
					try {
						//log.info("---我拿到了锁---:"+lock.toString());
						StAppActive where = new StAppActive().setDate(DateUtil.parse(req.getCreateTime()).toDateStr()).setAppId(appId);
						StAppActive stAppActive = stAppActiveMapper.selectOne(where);
						if(BeanUtil.isNotEmpty(stAppActive)) {
							stAppActiveMapper.updateByPrimaryKeySelective(stAppActive.setNumber(stAppActive.getNumber()+1));							
						}else {
							stAppActiveMapper.insertSelective(new StAppActive().setDate(req.getCreateTime()).setAppId(appId).setNumber(1));
						}
					}finally {
						//log.info("---我释放锁了---:"+lock.toString());
						lock.unlock();
					}
				}else {
					log.info("---我等待了{}秒还未拿到锁---:{}",constant.getRedis_lock_timeout_seconds(),lock.toString());
		    		throw DaasException.of().setMsg("服务器繁忙！");
				}
			}
		}
	}
	
	@Override
	public RespPaging<RespOperLogList> operLogList(ReqOperLogList req) throws Exception{
		Query query = new Query();
		if(StringUtils.isNotBlank(req.getTraceId()))
			query.addCriteria(Criteria.where("traceId").regex(Pattern.compile("^.*"+req.getTraceId()+".*$", Pattern.CASE_INSENSITIVE)));
		if(StringUtils.isNotBlank(req.getUserName())) 
			query.addCriteria(Criteria.where("userName").regex(Pattern.compile("^.*"+req.getUserName()+".*$", Pattern.CASE_INSENSITIVE)));
		if(StringUtils.isNotBlank(req.getAppName())) 
			query.addCriteria(Criteria.where("appName").regex(Pattern.compile("^.*"+req.getAppName()+".*$", Pattern.CASE_INSENSITIVE)));
		if(StringUtils.isNotBlank(req.getUri())) 
			query.addCriteria(Criteria.where("uri").regex(Pattern.compile("^.*"+req.getUri()+".*$", Pattern.CASE_INSENSITIVE)));
		if(StringUtils.isNotBlank(req.getIp())) 
			query.addCriteria(Criteria.where("ip").regex(Pattern.compile("^.*"+req.getIp()+".*$", Pattern.CASE_INSENSITIVE)));
		if(StringUtils.isNotBlank(req.getBody())) 
			query.addCriteria(Criteria.where("body").regex(Pattern.compile("^.*"+req.getBody()+".*$", Pattern.CASE_INSENSITIVE)));
		if(StringUtils.isNotBlank(req.getHeader())) 
			query.addCriteria(Criteria.where("header").regex(Pattern.compile("^.*"+req.getHeader()+".*$", Pattern.CASE_INSENSITIVE)));
		if(StringUtils.isNotBlank(req.getResp())) 
			query.addCriteria(Criteria.where("resp").regex(Pattern.compile("^.*"+req.getResp()+".*$", Pattern.CASE_INSENSITIVE)));
		
		if(StringUtils.isNotBlank(req.getStartTime()) && StringUtils.isNotBlank(req.getEndTime())) {
			Criteria criteria = new Criteria();
			criteria.andOperator(Criteria.where("createTime").gte(req.getStartTime()),Criteria.where("createTime").lte(req.getEndTime()));
			query.addCriteria(criteria);
		}else {
			if(StringUtils.isNotBlank(req.getStartTime())) 
				query.addCriteria(Criteria.where("createTime").gte(req.getStartTime()));
			if(StringUtils.isNotBlank(req.getEndTime())) 
				query.addCriteria(Criteria.where("createTime").lte(req.getEndTime()));			
		}
		long total = mongoTemplate.count(query, OperLog.class);
		
		query.skip(PageUtil.getStart(req.getPageNum()-1, req.getPageSize()));
		query.limit(req.getPageSize());
		query.with(Sort.by(Sort.Order.desc("createTime")));
		List<OperLog> list = mongoTemplate.find(query, OperLog.class);
		
		RespPaging<RespOperLogList> respPaging = new RespPaging<RespOperLogList>();
		respPaging.setTotal(total);
		respPaging.setList(JSONUtil.toList(new JSONArray(JSONUtil.toJsonStr(list)), RespOperLogList.class));
		return respPaging;
	}
	
}
