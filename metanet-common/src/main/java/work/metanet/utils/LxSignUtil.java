package work.metanet.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import work.metanet.api.device.vo.DeviceAppVo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LxSignUtil {
	
	public static String getDigestStr_sha256(Map<String, Object> map) {
		//return SecureUtil.signParams(DigestAlgorithm.SHA256, map, "&", "=",true);
		String signStr = getSignStr(map);
		log.info("signStr:{}",signStr);
		String sha256Str = getDigestStr_sha256(signStr);
		log.info("sha256Str:{}",sha256Str);
		return sha256Str;
	}
	
	public static String getSignStr(Map<String, Object> map) {
		return MapUtil.sortJoin(map, "&", "=", true);
	}
	
	public static String getDigestStr_sha256(String signStr) {
		return SecureUtil.sha256(signStr);
	}
	
	public static String sign_Sha256withAES(String secret,Map<String, Object> map) {
		return SecureUtil.aes(secret.getBytes()).encryptHex(getDigestStr_sha256(map));
	}
	
	public static String sign_AES(String secret,String digestStr) {
		AES aes = SecureUtil.aes(secret.getBytes());
		return aes.encryptHex(digestStr);
	}
	
	public static String unSign_AES(String secret,String sign) {
		return SecureUtil.aes(secret.getBytes()).decryptStr(sign);
	}
	
	public static void main(String[] args) {
		//test();
		System.out.println(unSign_AES("KpEhUHjyNCyhSl8G", "0CB110AE395E60AB57C757D5ED2CB0C0F22564120CBE28AC7ED0C0A2E5A4FB02CC3E5058140C093795599694D70E85CDD98704D4126FD6B2941C79CCD4B0BF40"));
	}
	
	private static void test() {
		Long timestamp = DateUtil.currentSeconds();
		System.out.println(timestamp);
		String a = SecureUtil.signParams(
				DigestAlgorithm.SHA256, 
				MapUtil.builder().put("timestamp",timestamp).put("appKey", "123").build(), 
				"&", 
				"=",
				true); 
		System.out.println(a);
		System.out.println(SecureUtil.sha256("appKey=123&appSecret=456&timestamp="+timestamp));
	}
	
	private static void test1() {
		List<DeviceAppVo> list = new ArrayList<DeviceAppVo>();
		list.add(new DeviceAppVo().setAppId("bbbbbbb"));
		list.add(new DeviceAppVo().setAppId("aaaaaaa"));
		list.add(new DeviceAppVo().setAppId("ccccccc"));
		
		List<DeviceAppVo> list2 = new ArrayList<DeviceAppVo>();
		list2.add(new DeviceAppVo().setAppId("dddddd"));
		list2.add(new DeviceAppVo().setAppId("eeeeee"));
		list2.add(new DeviceAppVo().setAppId("ffffff"));
		
		Long timestamp = DateUtil.currentSeconds();
		
		Map<Object, Object> map = MapUtil.builder().put("timestamp",timestamp).put("list2", JSONUtil.toJsonStr(list2)).put("list", JSONUtil.toJsonStr(list)).build();
		
		String signStr = MapUtil.sortJoin(map, "&", "=", true);
		System.out.println(signStr);
		String a = SecureUtil.md5(signStr);
		System.out.println(a);
	}
	
}
