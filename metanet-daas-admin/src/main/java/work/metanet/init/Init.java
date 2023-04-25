package work.metanet.init;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//gitee.com/ailoxi/luoxi.git
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import work.metanet.constant.ConstCacheKey;
import work.metanet.model.Region;
import work.metanet.server.dao.RegionMapper;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import lombok.Data;

@Order(1) //执行顺序
@Component
public class Init implements ApplicationRunner {
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Value("${lx.custom.aop.sms.sign.path:}")
	private String appSmsSignPath;
	@Autowired
	private RegionMapper regionMapper;
	
	/**
	@Autowired
	private ScheduleJobFactory scheduleJobFactory;
	*/
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		init_appSmsSign();
		cacheRegionAll();
	}
	
	void init_appSmsSign() {
		JSONArray jsonArray = JSONUtil.readJSONArray(FileUtil.file(appSmsSignPath), Charset.forName("UTF-8"));
		List<AppSmsSign> list=  JSONUtil.toList(jsonArray, AppSmsSign.class);
		list.forEach(appSmsSign->{
			stringRedisTemplate.opsForHash().put(ConstCacheKey.APP_SMS_SIGN.cacheKey(), appSmsSign.getPackageName(), appSmsSign.getSign());
		});
	}
	
	void cacheRegionAll() {
		List<Region> list = regionMapper.selectAll();
		TreeNodeConfig treeNodeConfig = TreeNodeConfig.DEFAULT_CONFIG.setIdKey("regionId").setNameKey("name").setParentIdKey("pid").setChildrenKey("child");
		List<Tree<String>> trees = TreeUtil.build(list, "0", treeNodeConfig, (treeNode, tree) -> {
            tree.setId(treeNode.getRegionId());
            tree.setParentId(treeNode.getPid());
            tree.setName(treeNode.getName());
        });
		stringRedisTemplate.opsForValue().set(ConstCacheKey.REGION_ALL.cacheKey(), JSONUtil.toJsonStr(trees));
		System.out.println("---------cache region all complete!");
	}
	
	@Data
	class AppSmsSign implements Serializable{
		private static final long serialVersionUID = 2546025342736216972L;
		private String packageName;
		private String sign;
	}
	
	
	
	
	/**
	private void jobUmengGetDailyData() throws Exception {
		ReqUmengGetDailyData req = new ReqUmengGetDailyData()
				.setApp(ConstApp.TAKE_READ.name())
				.setDate(DateUtil.formatDate(new Date()))
				;
		
		scheduleJobFactory.exec(
				ScheduleJob.of(JobUmengGetDailyData.class.getSimpleName())
				.setJobClass(JobUmengGetDailyData.class)
				.setCron("0 0 0/1 * * ?")
				.setJobDataMap(MapUtil.builder().put("req", req).build())
				);
		
		//友盟统计未达到统计要求暂时停止此job
		scheduleJobFactory.pauseJob("JobUmengGetDailyData");
		
	}*/
	
	/**
	private void jobUmengGetAllAppData() throws Exception {
		scheduleJobFactory.exec(
				ScheduleJob.of(JobUmengGetAllAppData.class.getSimpleName())
				.setJobClass(JobUmengGetAllAppData.class)
				.setCron("0 0 0/1 * * ? ")
				);
	}*/
	
	/**
	private void initResource() throws Exception {
		Set<String> keys = stringRedisTemplate.keys(ConstRedisCache.RESOURCE.cacheKey()+"*");
		for (String key : keys) {
			String json = stringRedisTemplate.opsForValue().get(key);
			if(StringUtils.isNotEmpty(json)) {
				
				
				List<ReqLoadingResource> resources = JSONUtil.toList(new JSONArray(json), ReqLoadingResource.class);
				
				resourceService.loadingResource(resources);
			}
		}
	}*/
}