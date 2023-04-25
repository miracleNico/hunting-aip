package work.metanet;

import java.math.BigDecimal;
import java.security.KeyPair;
import java.time.Duration;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import work.metanet.api.app.IAppService;
import work.metanet.api.app.protocol.ReqAppInfo;
import work.metanet.api.app.protocol.ReqAppList;
import work.metanet.api.app.protocol.ReqSaveApp;
import work.metanet.api.brand.IBrandService;
import work.metanet.api.brand.protocol.ReqBrandInfo;
import work.metanet.api.brand.protocol.ReqBrandList;
import work.metanet.api.brand.protocol.ReqSaveBrand;
import work.metanet.api.channel.IChannelService;
import work.metanet.api.channel.protocol.ReqChannelInfo;
import work.metanet.api.channel.protocol.ReqChannelList;
import work.metanet.api.channel.protocol.ReqSaveChannel;
import work.metanet.api.device.IDeviceService;
import work.metanet.api.device.protocol.ReqActivate;
import work.metanet.api.device.protocol.ReqDeviceInfo;
import work.metanet.api.device.protocol.ReqDeviceInfo.RespDeviceInfo;
import work.metanet.api.device.protocol.ReqDeviceList;
import work.metanet.api.device.protocol.ReqDeviceList.RespDeviceList;
import work.metanet.api.device.protocol.ReqSaveDevice;
import work.metanet.api.feedback.IFeedbackService;
import work.metanet.api.model.IModelService;
import work.metanet.api.model.protocol.ReqModelInfo;
import work.metanet.api.model.protocol.ReqModelList;
import work.metanet.api.model.protocol.ReqSaveModel;
import work.metanet.api.openDevice.IOpenDeviceService;
import work.metanet.api.openDevice.protocol.ReqOpenDeviceAuth;
import work.metanet.api.sn.ISerialNumberService;
import work.metanet.api.sn.protocol.ReqSaveSerialNumber;
import work.metanet.api.sn.protocol.ReqSerialNumberInfo;
import work.metanet.api.sn.protocol.ReqSerialNumberList;
import work.metanet.api.upgradePlan.protocol.ReqUpgrade.RespUpgrade;
import work.metanet.api.user.IUserService;
import work.metanet.api.user.protocol.ReqUserList;
import work.metanet.api.version.IAppVersionService;
import work.metanet.api.version.protocol.ReqAppVersionInfo;
import work.metanet.api.version.protocol.ReqAppVersionList;
import work.metanet.api.version.protocol.ReqSaveAppVersion;
import work.metanet.api.versionModule.IAppVersionModuleService;
import work.metanet.base.RespPaging;
import work.metanet.constant.ConstAppType;
import work.metanet.constant.ConstSource;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.Console;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import cn.hutool.json.JSONUtil;

@SpringBootTest
class ApplicationTest {	
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Resource
	private RedisTemplate<String, Integer> redisTemplate;
	//远程调用注入接口类
	@DubboReference IUserService iUserSerivce;
	//本地调用注入实现类
	@Autowired IDeviceService deviceService;
	@Autowired IChannelService channelService;
	@Autowired IBrandService brandService;
	@Autowired IModelService modelService;
	@Autowired IAppService appService;
	@Autowired IAppVersionService appVersionService;
	@Autowired ISerialNumberService serialNumberService;
	@DubboReference IFeedbackService feedbackService;
	@Autowired IAppVersionModuleService appVersionModuleService;
	@Autowired IOpenDeviceService openDeviceService;
	
	@Test
	void openAuth() throws Exception {
		Console.print(openDeviceService.auth(new ReqOpenDeviceAuth().setAppId("5f693d595dc60f66c14b70c6").setDeviceId("123456789")));
	}
	
	@Test
	void redis2() {
		stringRedisTemplate.opsForValue().set("test", "{}",Duration.ofSeconds(60*5));
		RespUpgrade resp = JSONUtil.toBean(stringRedisTemplate.opsForValue().get("test"), RespUpgrade.class);
		System.out.println(resp);
	}
	
	@Test
	void redis() {
		System.out.println(redisTemplate.opsForValue().get("qqq"));
		redisTemplate.opsForValue().set("qqq", 123);
		System.out.println(redisTemplate.opsForValue().get("qqq"));
	}
	
	public static void main(String[] args) {
		//私钥签名->公钥验签
		//公钥加密->私钥解密
		//sort();
		AES_test();
	}
	
	static void AES_test() {
		byte[] secret = SecureUtil.aes().getSecretKey().getEncoded();
		System.out.println(HexUtil.encodeHexStr(secret));
		String str = SecureUtil.aes("LxM5e8nb6cnljYEutYzW7OmDCZDDAe22".getBytes()).encryptHex("哆ssssssssssssssssssssssssssssssssssssssssssssssssssssss哆");
		System.out.println(str);
		System.out.println(SecureUtil.aes("LxM5e8nb6cnljYEutYzW7OmDCZDDAe22".getBytes()).decryptStr(str));
	}
	
	static void AES对称加密与解密3() {
		byte[] secret = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
		String secretStr = HexUtil.encodeHexStr(secret);
		Console.log("secretStr:"+secretStr);
		String encryptHex = SecureUtil.aes(secret).encryptHex("哆哆");
		Console.log("encryptStr:"+encryptHex);
		String decryptStr = SecureUtil.aes(secret).decryptStr(encryptHex);
		Console.log("decryptStr:"+decryptStr);
	}
	
	static void AES对称加密与解密2() {
		String content = "hello哆哆";
		//随机生成密钥
		byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
		//构建
		SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);
		//加密
		byte[] encrypt = aes.encrypt(content);
		//解密
		byte[] decrypt = aes.decrypt(encrypt);
		//加密为16进制表示
		String encryptHex = aes.encryptHex(content);
		//解密为字符串
		String decryptStr = aes.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
		Console.log(decryptStr);
	}
	
	static void AES对称加密与解密() {
		
		byte[] secretKey = SecureUtil.aes().getSecretKey().getEncoded();
		//String secretKeyStr = HexUtil.encodeHexStr(secretKey);
		//Console.log(secretKeyStr);
		String secretKeyStr = "04139649c07ddd389994011981d546ce";
		String encrypt = SecureUtil.aes(secretKeyStr.getBytes()).encryptHex("hello哆哆");//.encryptBase64("hello哆哆");
		Console.log(encrypt);
		
		String decrypt = SecureUtil.aes(secretKeyStr.getBytes()).decryptStr(encrypt);
		Console.log(decrypt);
	}
	
	static void sign() {
		byte[] data = "我是一段测试字符串".getBytes();
		Sign sign = SecureUtil.sign(SignAlgorithm.MD5withRSA);
		//签名
		byte[] signed = sign.sign(data);
		//验证签名
		boolean verify = sign.verify(data, signed);
		Console.log(verify);
	}
	
	static void sort() {
		Map<?, ?> map = MapUtil.builder().put("bd", "4").put("bc", "3").put("ba", null).put("bb", "").put("c", "2").put("a", "1").put("a2", "1").put("a1", "1").build();
		Console.log(MapUtil.sortJoin(map, "&", "=", true, ""));
	}
	
	static void 已知私钥和密文_解密密文() {
		String PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIL7pbQ+5KKGYRhw7jE31hmA"
		        + "f8Q60ybd+xZuRmuO5kOFBRqXGxKTQ9TfQI+aMW+0lw/kibKzaD/EKV91107xE384qOy6IcuBfaR5lv39OcoqNZ"
		        + "5l+Dah5ABGnVkBP9fKOFhPgghBknTRo0/rZFGI6Q1UHXb+4atP++LNFlDymJcPAgMBAAECgYBammGb1alndta"
		        + "xBmTtLLdveoBmp14p04D8mhkiC33iFKBcLUvvxGg2Vpuc+cbagyu/NZG+R/WDrlgEDUp6861M5BeFN0L9O4hz"
		        + "GAEn8xyTE96f8sh4VlRmBOvVdwZqRO+ilkOM96+KL88A9RKdp8V2tna7TM6oI3LHDyf/JBoXaQJBAMcVN7fKlYP"
		        + "Skzfh/yZzW2fmC0ZNg/qaW8Oa/wfDxlWjgnS0p/EKWZ8BxjR/d199L3i/KMaGdfpaWbYZLvYENqUCQQCobjsuCW"
		        + "nlZhcWajjzpsSuy8/bICVEpUax1fUZ58Mq69CQXfaZemD9Ar4omzuEAAs2/uee3kt3AvCBaeq05NyjAkBme8SwB0iK"
		        + "kLcaeGuJlq7CQIkjSrobIqUEf+CzVZPe+AorG+isS+Cw2w/2bHu+G0p5xSYvdH59P0+ZT0N+f9LFAkA6v3Ae56OrI"
		        + "wfMhrJksfeKbIaMjNLS9b8JynIaXg9iCiyOHmgkMl5gAbPoH/ULXqSKwzBw5mJ2GW1gBlyaSfV3AkA/RJC+adIjsRGg"
		        + "JOkiRjSmPpGv3FOhl9fsBPjupZBEIuoMWOC8GXK/73DHxwmfNmN7C9+sIi4RBcjEeQ5F5FHZ";

		RSA rsa = new RSA(PRIVATE_KEY, null);

		String a = "2707f9FD4288CEF302C972058712F24A5F3EC62C5A14AD2FC59DAB93503AA0FA17113A020EE4EA35EB53F"
		        + "75F36564BA1DABAA20F3B90FD39315C30E68FE8A1803B36C29029B23EB612C06ACF3A34BE815074F5EB5AA3A"
		        + "C0C8832EC42DA725B4E1C38EF4EA1B85904F8B10B2D62EA782B813229F9090E6F7394E42E6F44494BB8";
		
		Console.log(rsa.decryptStr(a, KeyType.PrivateKey));
	}
	
	static void RSA_自助生成密钥对_私钥加密() {
		KeyPair pair = SecureUtil.generateKeyPair("RSA");
		
		String privateKeyBase64 = Base64.encode(pair.getPrivate().getEncoded());
		Console.log("privateKeyBase64:"+privateKeyBase64);
		
		String publicKeyBase64 = Base64.encode(pair.getPublic().getEncoded());
		Console.log("publicKeyBase64:"+publicKeyBase64);
		
		RSA rsa = new RSA(privateKeyBase64,publicKeyBase64);
		
		String encrypt = HexUtil.encodeHexStr(rsa.encrypt("自助生成密钥对", KeyType.PrivateKey));
		Console.log("encrypt:"+encrypt);
		Console.log(rsa.decryptStr(encrypt, KeyType.PublicKey));
	}
	
	static void RSA_自助生成密钥对() {
		KeyPair pair = SecureUtil.generateKeyPair("RSA");
		String privateKeyBase64 = Base64.encode(pair.getPrivate().getEncoded());
		String PublicKeyBase64 = Base64.encode(pair.getPublic().getEncoded());
		RSA rsa = new RSA(privateKeyBase64,PublicKeyBase64);
		byte[] encrypt = rsa.encrypt("自助生成密钥对", KeyType.PrivateKey);
		byte[] decrypt = rsa.decrypt(encrypt, KeyType.PublicKey);
		System.out.println(StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8));
	}
	
	//签名
	static void RSA_私钥加密_公钥解密() {
		RSA rsa = new RSA();
		//私钥加密，公钥解密
		byte[] encrypt = rsa.encrypt(StrUtil.bytes("私钥加密_公钥解密", CharsetUtil.CHARSET_UTF_8), KeyType.PrivateKey);
		byte[] decrypt = rsa.decrypt(encrypt, KeyType.PublicKey);
		System.out.println(StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8));
	}
	
	//加密
	static void RSA_公钥加密_私钥解密() {
		RSA rsa = new RSA();
		//公钥加密，私钥解密
		byte[] encrypt = rsa.encrypt(StrUtil.bytes("公钥加密_私钥解密", CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
		byte[] decrypt = rsa.decrypt(encrypt, KeyType.PrivateKey);
		System.out.println(StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8));
	}
	
	static void RSA_秘钥对() {
		//参考:https://segmentfault.com/a/1190000010837277
		RSA rsa = new RSA();
		
		byte[] privateKey = rsa.getPrivateKey().getEncoded();
		byte[] PublicKey = rsa.getPublicKey().getEncoded();
		
		String privateKeyBase64 = rsa.getPrivateKeyBase64();
		String PublicKeyBase64 = rsa.getPublicKeyBase64();
		
		System.out.println("privateKey:"+privateKey);
		System.out.println("PublicKey:"+PublicKey);
		System.out.println("privateKeyBase64:"+privateKeyBase64);
		System.out.println("PublicKeyBase64:"+PublicKeyBase64);
	}
	
	@Test
	void getFeedbackOption()throws Exception{
		System.out.println(JSONUtil.toJsonStr(feedbackService.getFeedbackOption()));
	}
	
	void viewAppVersionModule()throws Exception{
		System.out.println(JSONUtil.toJsonStr(appVersionModuleService.viewAppVersionModule("com.ailexi.chindle", "1.0.1")));
	}
	
	@Test
	void userList() throws Exception {
		System.out.println(JSONUtil.toJsonStr(iUserSerivce.userList(new ReqUserList())));;
	}
	
	@Test
	void activate() throws Exception {
		ReqActivate activate = new ReqActivate();
		activate.setSnCode("098B1NHGVBR");
		activate.setUuid("66666666");
		activate.setMac("66666666");
		activate.setBrandName("魅族");
		activate.setModelName("mz100");
		activate.setPackageName("com.ailexi.educate");
		activate.setWiredMac("11");
		activate.setWirelessMac("22");
		activate.setImei("33");
		activate.setImei2("44");
		activate.setBluetooth("55");
		activate.setFirmwareInfo("66");
		System.out.println(JSONUtil.toJsonStr(deviceService.activate(activate)));

		//System.out.println(JSONUtil.toJsonStr(deviceService.deviceAuth(activate)));
	}
	
	@Test
	void deviceList() throws Exception {
		ReqDeviceList req = new ReqDeviceList();
		req.setBrand("诺");
		RespPaging<RespDeviceList> respPaging = deviceService.deviceList(req);
		System.out.println(JSONUtil.toJsonPrettyStr(respPaging));
	}
	
	@Test
	void deviceDetail() throws Exception{
		ReqDeviceInfo req = new ReqDeviceInfo();
		req.setDeviceId("DV10150");
		RespDeviceInfo detail = deviceService.deviceInfo(null,req);
		System.out.println(JSONUtil.toJsonPrettyStr(detail));
	}
	
	@Test
	void saveDevice() throws Exception {
		ReqSaveDevice req = new ReqSaveDevice();
		//req.setDeviceId("DV1017");
		req.setDeviceName("机器人123");
		req.setBrandId("BD1012");
		req.setModelId("MD1014");
		req.setUuid("666666");
		deviceService.saveDevice(req);
	}
	
	/**
	 * @Description: 渠道列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Test
	void channelList() throws Exception{
		ReqChannelList req = new ReqChannelList();
		System.out.println(JSONUtil.toJsonPrettyStr(channelService.channelList(req)));
	}
	
	/**
	 * @Description: 渠道详情
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Test
	void channelInfo() throws Exception{
		ReqChannelInfo req = new ReqChannelInfo();
		req.setChannelId("111");
		System.out.println(JSONUtil.toJsonPrettyStr(channelService.channelInfo(req)));
	}
	
	/**
	 * @Description: 保存渠道
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Test
	void saveChannel() throws Exception{
		ReqSaveChannel req = new ReqSaveChannel();
		req.setChannelId("CN1016");
		req.setChannelName("飞利浦");
		req.setRemark("不错的客户3");
		channelService.saveChannel(req);
	}
	
	/**
	 * @Description: 删除渠道
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Test
	void removeChannel() throws Exception{
		//channelService.removeChannel("123");
	}
	
	
	@Test
	void brandList() throws Exception{
		ReqBrandList req = new ReqBrandList();
		System.out.println(JSONUtil.toJsonPrettyStr(brandService.brandList(req)));
	}
	
	@Test
	void brandInfo() throws Exception{
		ReqBrandInfo req = new ReqBrandInfo();
		req.setBrandId("BD1013");
		System.out.println(JSONUtil.toJsonPrettyStr(brandService.brandInfo(req)));
	}
	
	@Test
	void saveBrand() throws Exception{
		ReqSaveBrand req = new ReqSaveBrand();
		req.setBrandName("戴尔");
		req.setSource(ConstSource.IMPORT.getVal());
		req.setRemark("品牌备注一下");
		System.out.println(JSONUtil.toJsonPrettyStr(brandService.saveBrand(req)));
	}
	
	@Test
	void removeBrand() throws Exception{
		//brandService.removeBrand("BD1014");
	}
	
	
	@Test
	void modelList() throws Exception{
		ReqModelList req = new ReqModelList();
		System.out.println(JSONUtil.toJsonPrettyStr(modelService.modelList(req)));
	}
	
	@Test
	void modelInfo() throws Exception{
		ReqModelInfo req = new ReqModelInfo();
		req.setModelId("MD1016");
		System.out.println(JSONUtil.toJsonPrettyStr(modelService.modelInfo(req)));
	}
	
	@Test
	void saveModel() throws Exception{
		ReqSaveModel req = new ReqSaveModel();
		req.setModelName("X9");
		req.setBrandId("BD1014");
		req.setRemark("型号备注一下2");
		req.setSource(ConstSource.IMPORT.getVal());
		System.out.println(JSONUtil.toJsonPrettyStr(modelService.saveModel(req)));
	}
	
	@Test
	void removeModel() throws Exception{
		//modelService.removeModel("MD1019");
	}
	
	
	
	/**
	 * @Description: 产品列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Test
	void appList() throws Exception{
		ReqAppList req = new ReqAppList();
		System.out.println(JSONUtil.toJsonPrettyStr(appService.appList(req)));
	}
	
	/**
	 * @Description: 产品详情
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Test
	void appInfo() throws Exception{
		ReqAppInfo req = new ReqAppInfo();
		req.setAppId("1");
		System.out.println(JSONUtil.toJsonPrettyStr(appService.appInfo(req)));
	}
	
	/**
	 * @Description: 保存产品
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Test
	void saveApp() throws Exception{
		ReqSaveApp req = new ReqSaveApp();
		req.setAppId("faed0f4a0b164a0f842bc8f0377f4454");
		req.setAppName("幼教版3");
		req.setAppType(ConstAppType.APP.getVal());
		req.setInstruction("产品说明.........");
		req.setRemark("对外测试");
		appService.saveApp(req);
	}
	
	/**
	 * @Description: 删除产品
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Test
	void removeApp() throws Exception{
		//appService.removeApp("faed0f4a0b164a0f842bc8f0377f4454");
	}
	
	
	/**
	 * @Description: 版本列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Test
	void appVersionList() throws Exception{
		ReqAppVersionList req = new ReqAppVersionList();
		System.out.println(JSONUtil.toJsonPrettyStr(appVersionService.appVersionList(req)));
	}
	
	/**
	 * @Description: 版本详情
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Test
	void appVersionInfo() throws Exception{
		ReqAppVersionInfo req = new ReqAppVersionInfo();
		req.setVersionId("x1.0");
		System.out.println(JSONUtil.toJsonPrettyStr(appVersionService.appVersionInfo(req)));
	}
	
	/**
	 * @Description: 保存版本
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Test
	void saveAppVersion() throws Exception{
		ReqSaveAppVersion req = new ReqSaveAppVersion();
		req.setVersionId("47dabf5279c348698f17869fa871b9a2");
		req.setAppId("faed0f4a0b164a0f842bc8f0377f4454");
		req.setVersionName("幼教版-升级版333");
		req.setVersionCode("3.0");
		req.setFileSize(new BigDecimal(10240000));
		req.setMd5("aaaaaaaaaaaa");
		req.setUrl("/group1/M00/01/0B/rBAAEV3WCmKAf-T2AAA4xQGMcEs207.png");
		req.setRemark("测试中");
		req.setInstruction("试用期免费");
		System.out.println(JSONUtil.toJsonPrettyStr(appVersionService.saveAppVersion(req)));
	}
	
	/**
	 * @Description: 删除版本
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Test
	void removeAppVersion() throws Exception{
		//appVersionService.removeAppVersion("47dabf5279c348698f17869fa871b9a2");
	}
	
	
	/**
	 * @Description: SN码列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Test
	void serialNumberList() throws Exception{
		ReqSerialNumberList req = new ReqSerialNumberList();
		System.out.println(JSONUtil.toJsonPrettyStr(serialNumberService.serialNumberList(req)));
	}
	
	/**
	 * @Description: SN码详情
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Test
	void serialNumberInfo() throws Exception{
		ReqSerialNumberInfo req = new ReqSerialNumberInfo();
		req.setSnCode("111");
		System.out.println(JSONUtil.toJsonPrettyStr(serialNumberService.serialNumberInfo(req)));
	}
	
	/**
	 * @Description: 保存SN码
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Test
	void saveSerialNumber() throws Exception{
		ReqSaveSerialNumber req = new ReqSaveSerialNumber();
		//req.setChannelId("CN1016");
		//req.setRemark("SN码批量生成");
		//req.setSnCodeNum(100);
		serialNumberService.saveSerialNumber(req);
	}
	
	/**
	 * @Description: 删除SN码
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Test
	void removeSerialNumber() throws Exception{
		//serialNumberService.removeSerialNumber("79479b2d5de042318432cccbd52e72bc");
	}
	
	
	

}
