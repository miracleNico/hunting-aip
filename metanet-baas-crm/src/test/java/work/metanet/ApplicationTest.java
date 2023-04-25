package work.metanet;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import work.metanet.constant.Constant;
import work.metanet.utils.CosUtil;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;

@SpringBootTest
class ApplicationTest {
	
	@Autowired
	Constant constant;
	@Autowired
	CosUtil cosUtil;
	
	@Test
	void mongodb文件处理() {
		Set<String> set = FileUtil.readLines("E:\\luoxi\\mongodb文件.txt", CharsetUtil.CHARSET_UTF_8, new HashSet<String>());
		System.out.println(set.size());
		int i = 1;
		for (String str : set) {
			String filePath = "G:\\111.230.182.89备份\\fastdfs\\data"+StrUtil.removePrefix(str, "group1/M00");
			if(!FileUtil.exist(filePath)) {
				System.out.println("路径不存在:"+filePath);
				continue;
			}
			cosUtil.upload(str, FileUtil.file(filePath));
			System.out.println(i);
			i++;
		}
		System.out.println(set.size());
	}
	
}
