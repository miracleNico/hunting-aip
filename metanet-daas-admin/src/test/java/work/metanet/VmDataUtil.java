package work.metanet;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

public class VmDataUtil {

	//static File file = FileUtil.file("G:\\虚拟数据\\"+StrUtil.removeSuffix(Thread.currentThread().getStackTrace()[2].getFileName(), ".java")+".xlsx");
	static String tag = "0";
	static String baseIdPath = "G:\\虚拟数据\\globalId.txt";
	static Integer start_device_id = Integer.parseInt(FileUtil.readLines(baseIdPath,Charset.defaultCharset()).get(0));
	//序列号长度
	static Integer serial_number_length = 12;
	//偏移的时间字段，例如时、分、秒等
	static DateField device_activate_time_offset_dateField = DateField.SECOND;
	//偏移最小量，可以为负数表示过去的时间（包含）
	static Integer device_activate_time_offset_min = 60*60*24*1;
	//偏移最小量，可以为负数表示过去的时间（包含）
	static Integer user_create_time_offset_min = 10;
	//偏移最大量，可以为负数表示过去的时间（不包含）
	static Integer user_create_time_offset_max = 60*5;
	static String[] basePhonePrefix = {"133","149","153","173","177","180","181","189","199","130","131","132","145","155","156","166","171","175","176","185","186","166","134","135","136","137","138","139","147","150","151","152","157","158","159","172","178","182","183","184","187","188","198"};
	static String basePhoneSuffix = "01234567890123456789";
	static String baseMacString  = "01234567890123456789abcdef01234567890123456789";
	
	public static void exec(
			String fileName,
			String device_create_time,
			Integer device_count,
			Integer device_activate_count,
			String app_id,
			String version_id,
			String brand_id,
			String brand_name,
			String model_id,
			String model_name,
			String device_serial_number_prefix
			) {
		File file = FileUtil.file("G:\\虚拟数据\\"+fileName+".xlsx");
		if(file.exists()) {
			file.delete();
		}
		List<Map<String,Object>> devices = new ArrayList<Map<String,Object>>();
		deviceWriter(file, device_count, device_activate_count, app_id, version_id, brand_id, brand_name, model_id, model_name, device_serial_number_prefix, DateUtil.parse(device_create_time), devices);
		deviceAppWriter(file, app_id, device_activate_count, DateUtil.parse(device_create_time), devices);
		userWriter(file, app_id, devices);
		userLoginWriter(file, version_id, devices);
		Console.log("done->"+fileName);
	}
	
	private static void deviceWriter(
			File file,
			Integer device_count,
			Integer device_activate_count,
			String app_id,
			String version_id,
			String brand_id,
			String brand_name,
			String model_id,
			String model_name,
			String device_serial_number_prefix,
			Date device_create_time,
			List<Map<String,Object>> devices
			) {
		ExcelWriter excelWriter = ExcelUtil.getWriter(file, "t_device");
		excelWriter.setColumnWidth(-1, 20);
		List<String> heads = Arrays.asList("device_id","device_name","wireless_mac","serial_number","uuid","brand_id","model_id","create_time","update_time","tag");
		excelWriter.writeHeadRow(heads);
		for (int i = 1; i <= device_count; i++) {
			String device_id = "DV"+start_device_id++;
			String device_name = StrUtil.join("-", brand_name,model_name,device_id);
			String wireless_mac = StrUtil.join(":", RandomUtil.randomString(baseMacString,2),RandomUtil.randomString(baseMacString,2),RandomUtil.randomString(baseMacString,2),RandomUtil.randomString(baseMacString,2),RandomUtil.randomString(baseMacString,2),RandomUtil.randomString(baseMacString,2));
			String serial_number = device_serial_number_prefix+RandomUtil.randomString(serial_number_length-device_serial_number_prefix.length()).toUpperCase();
			String uuid = IdUtil.fastSimpleUUID();
			Date create_time = device_create_time;
			Date update_time = create_time;
			List<Object> data = Arrays.asList(device_id,device_name,wireless_mac,serial_number,uuid,brand_id,model_id,create_time,update_time,tag);
			excelWriter.writeRow(data);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("device_id", device_id);
			devices.add(map);
		}
		excelWriter.flush();
		excelWriter.close();
	}
	
	static void deviceAppWriter(
			File file,
			String app_id,
			Integer device_activate_count,
			Date device_create_time,
			List<Map<String,Object>> devices
			) {
		ExcelWriter excelWriter = ExcelUtil.getWriter(file, "t_device_app");
		excelWriter.setColumnWidth(-1, 20);
		List<String> heads = Arrays.asList("device_app_id","device_id","app_id","activate_time","create_time","tag");
		excelWriter.writeHeadRow(heads);
		Integer device_activate_time_offset_max = Convert.toInt(DateUtil.between(device_create_time, DateUtil.date(), DateUnit.SECOND));
		AtomicInteger activate_count = new AtomicInteger(0);
		devices.forEach(map->{
			String device_app_id = IdUtil.fastSimpleUUID();
			String device_id = String.valueOf(map.get("device_id"));
			Date activate_time = null;
			while (true) {
				activate_time = RandomUtil.randomDate(device_create_time, device_activate_time_offset_dateField, device_activate_time_offset_min, device_activate_time_offset_max);
				if(DateUtil.hour(activate_time, true)>7) break;
			}
			Date create_time = activate_time;
			if(activate_count.get()>=device_activate_count) {
				activate_time = null;
			}
			List<Object> data = Arrays.asList(device_app_id,device_id,app_id,activate_time,create_time,tag);
			excelWriter.writeRow(data);
			
			map.put("device_activate_time", activate_time);
			activate_count.set(activate_count.get()+1);
		});
		excelWriter.flush();
		excelWriter.close();
	}
	
	private static void userWriter(
			File file,
			String app_id,
			List<Map<String,Object>> devices
			) {
		ExcelWriter excelWriter = ExcelUtil.getWriter(file, "t_user");
		excelWriter.setColumnWidth(-1, 20);
		List<String> heads = Arrays.asList("user_id","app_id","user_name","password","nick_name","phone","create_time","update_time","tag");
		excelWriter.writeHeadRow(heads);
		devices.forEach(map->{
			if(ObjectUtil.isEmpty(map.get("device_activate_time"))) return;
			String user_id = IdUtil.fastSimpleUUID();
			String phone = RandomUtil.randomEle(basePhonePrefix)+RandomUtil.randomString(basePhoneSuffix, 8);
			String user_name = phone; 
			String password = "4a7d1ed414474e4033ac29ccb8653d9b";
			String nick_name = phone.substring(phone.length()-6,phone.length());
			Date create_time = RandomUtil.randomDate(Convert.toDate(map.get("device_activate_time")), device_activate_time_offset_dateField, user_create_time_offset_min, user_create_time_offset_max);
			Date update_time = create_time;
			List<Object> data = Arrays.asList(user_id,app_id,user_name,password,nick_name,phone,create_time,update_time,tag);
			excelWriter.writeRow(data);
			
			map.put("user_id", user_id);
			map.put("user_create_time", create_time);
		});
		excelWriter.flush();
		excelWriter.close();
	}
	
	private static void userLoginWriter(
			File file,
			String version_id,
			List<Map<String,Object>> devices
			) {
		ExcelWriter excelWriter = ExcelUtil.getWriter(file, "t_user_login");
		excelWriter.setColumnWidth(-1, 20);
		List<String> heads = Arrays.asList("user_login_id","user_id","device_id","version_id","create_time","tag");
		excelWriter.writeHeadRow(heads);
		devices.forEach(map->{
			if(ObjectUtil.isEmpty(map.get("user_id"))) return;
			String user_login_id = IdUtil.fastSimpleUUID();
			String user_id = String.valueOf(map.get("user_id"));
			String device_id = String.valueOf(map.get("device_id"));
			Date create_time = Convert.toDate(map.get("user_create_time"));
			List<Object> data = Arrays.asList(user_login_id,user_id,device_id,version_id,create_time,tag);
			excelWriter.writeRow(data);
		});
		excelWriter.flush();
		excelWriter.close();
	}
}
