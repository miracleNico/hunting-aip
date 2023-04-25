package work.metanet.utils;

import java.util.HashMap;
import java.util.Map;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;

public class LxKeyUtil {
	
	private static final String baseString  = "0123456789qwertyuiopasdfghjklzcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
	private static final String deviceSnString  = StrUtil.concat(true, baseString, "01234567890123456789");
	
	public static String appKey() {
		return key(22,"Lx");
	}
	
	public static String appSecret() {
		return key(16,"");
	}
	
	private static String key(int length,String prefix) {
		return StrUtil.addPrefixIfNot(RandomUtil.randomString(baseString,length), prefix);
	}
	
	public static String appSerialNumber() {
		return RandomUtil.randomStringUpper(12);
	}
	
	public static String deviceSn(String channelTag,Integer suffixNumber) {
		return StrUtil.addPrefixIfNot(RandomUtil.randomString(deviceSnString,suffixNumber), "LX".concat(channelTag)).toUpperCase();
	}
	
	private static void qingdanSN() {
		Map<String, Object> map = new HashMap<String, Object>();
		while(map.size()<100000) {
			map.put(deviceSn("QDMH"+DateUtil.format(DateUtil.date(), "yyMMdd"),4), "");
		}
		for (String key : map.keySet()) {
			System.out.println(key);
		}
	}
	
	private static void aibinguoSN() {
		Map<String, Object> map = new HashMap<String, Object>();
		while(map.size()<100000) {
			map.put(deviceSn("ABGX"+DateUtil.format(DateUtil.date(), "yyMMdd"),4), "");
		}
		for (String key : map.keySet()) {
			System.out.println(key);
		}
	}
	
	public static void main(String[] args) {
		//System.out.println(appKey());
		//System.out.println(appSecret());
		//qingdanSN();
		for (int i = 0; i < 16; i++) {
			System.out.println(appSecret());;
		}
	}
	
	
	
}
