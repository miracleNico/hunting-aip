package work.metanet.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.CannedAccessControlList;
import com.qcloud.cos.model.GeneratePresignedUrlRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;

@Component
public class CosUtil {
	
	@Value("${tencent.cloud.accessKey:}")
	private String accessKey;
	@Value("${tencent.cloud.secretKey:}")
	private String secretKey;
	@Value("${tencent.cloud.cos.region:}")
	private String region;
	@Value("${tencent.cloud.cos.bucketName:}")
	private String bucketName;
	private Integer expirationSeconds = 60;
	
	public String upload(MultipartFile multipartFile) {
		String key = StrUtil.concat(true, "other/",IdUtil.fastSimpleUUID(),".",FileUtil.extName(multipartFile.getOriginalFilename()));
		URL url = upload(key,multipartFile,CannedAccessControlList.PublicRead);
		return url.getProtocol()+"://"+url.getHost()+url.getPath();
	}
	
	public String upload(File file) {
		String key = StrUtil.concat(true, "other/",IdUtil.fastSimpleUUID(),".",FileUtil.extName(file));
		URL url = upload(key,file,CannedAccessControlList.PublicRead);
		return url.getProtocol()+"://"+url.getHost()+url.getPath();
	}
	
	public String upload(String key,MultipartFile multipartFile) {
		URL url = upload(key,multipartFile,CannedAccessControlList.PublicRead);
		return url.getProtocol()+"://"+url.getHost()+url.getPath();
	}
	
	public String upload(String key,File file) {
		URL url = upload(key,file,CannedAccessControlList.PublicRead);
		return url.getProtocol()+"://"+url.getHost()+url.getPath();
	}
	
	public String uploadPv(String key,File file) {
		URL url = upload(key,file,CannedAccessControlList.Private);
		return url.toString();
	}
	
	public String uploadPv(String key,MultipartFile multipartFile) {
		URL url = upload(key,multipartFile,CannedAccessControlList.Private);
		return url.toString();
	}
	
	private URL upload(String key,MultipartFile multipartFile,CannedAccessControlList cannedAccessControlList) {
		ObjectMetadata objectMetadata = new ObjectMetadata();
        // 从输入流上传必须制定content length, 否则http客户端可能会缓存所有数据，存在内存OOM的情况
        objectMetadata.setContentLength(multipartFile.getSize());
        PutObjectRequest putObjectRequest;
        URL url = null;
		try {
			putObjectRequest = new PutObjectRequest(bucketName, key, multipartFile.getInputStream(), objectMetadata);
			putObjectRequest.setCannedAcl(cannedAccessControlList);
			url = upload(key, putObjectRequest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return url;
	}
	
	private URL upload(String key,File file,CannedAccessControlList cannedAccessControlList) {
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
        putObjectRequest.setCannedAcl(cannedAccessControlList);
        return upload(key, putObjectRequest);
	}
	
	private URL upload(String key,PutObjectRequest putObjectRequest) {
		COSCredentials cred = new BasicCOSCredentials(accessKey, secretKey);
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        COSClient cosClient = new COSClient(cred, clientConfig);
        URL url = null;
        try {
            cosClient.putObject(putObjectRequest);
            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, key, HttpMethodName.GET);
            generatePresignedUrlRequest.setExpiration(DateUtil.offsetSecond(DateUtil.date(), expirationSeconds));
            url = cosClient.generatePresignedUrl(generatePresignedUrlRequest);
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }finally {
        	//关闭客户端
        	cosClient.shutdown();
		}
        return url;
	}
	
	public void deleteFile(String key) {
		COSCredentials cred = new BasicCOSCredentials(accessKey, secretKey);
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        COSClient cosClient = new COSClient(cred, clientConfig);
        try {
			cosClient.deleteObject(bucketName, key);
		} catch (CosServiceException e) {
			e.printStackTrace();
		} catch (CosClientException e) {
			e.printStackTrace();
		}finally {
			cosClient.shutdown();			
		}
	}
	
	/**
	 * @Description: 过滤url域名
	 * @Author wanbo
	 * @DateTime 2020/03/03
	 */
	public String filterUrlDomain(String url) {
    	//return StrUtil.subBetween(url, "myqcloud.com/", "?sign=");
		if (StrUtil.isBlank(url)) {
			return url;
		}
		return URLUtil.getPath(url);
    }
	
	/**
     * @Description: 获取访问的url
     * @Author wanbo
     * @DateTime 2020/03/03
     */
	public String getAccessUrl(String key) {
		if(StrUtil.isBlank(key)) return key;
		if(StrUtil.startWith(key, "http")) return key;
		COSCredentials cred = new BasicCOSCredentials(accessKey, secretKey);
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        COSClient cosClient = new COSClient(cred, clientConfig);
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, key, HttpMethodName.GET);
        URL url = null;
		try {
			url = cosClient.generatePresignedUrl(generatePresignedUrlRequest);
		} catch (CosClientException e) {
			e.printStackTrace();
		}finally {
			cosClient.shutdown();
		}
        return url.getProtocol()+"://"+url.getHost()+url.getPath();
	}
	
	public String getAccessUrlPv(String key) {
		if(StrUtil.isBlank(key)) return key;
		if(StrUtil.startWith(key, "http")) return key;
		COSCredentials cred = new BasicCOSCredentials(accessKey, secretKey);
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        COSClient cosClient = new COSClient(cred, clientConfig);
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, key, HttpMethodName.GET);
        generatePresignedUrlRequest.setExpiration(DateUtil.offsetSecond(DateUtil.date(), 30));
        URL url = null;
		try {
			url = cosClient.generatePresignedUrl(generatePresignedUrlRequest);
		} catch (CosClientException e) {
			e.printStackTrace();
		}finally {
			cosClient.shutdown();			
		}
        return url.toString();
	}
	
	public static void main(String[] args) {
		COSCredentials cred = new BasicCOSCredentials("AKID2OZcTBcLxUze5Kf4OUKwTqjRtJUPfTcV", "nYSChvue8MJB3g28B7sS1KNBpDLCnIad");
        ClientConfig clientConfig = new ClientConfig(new Region("ap-guangzhou"));
        COSClient cosClient = new COSClient(cred, clientConfig);
        PutObjectRequest putObjectRequest = new PutObjectRequest("test-1258463718", "test.png", FileUtil.file("G:/test.jpg"));
        putObjectRequest.setCannedAcl(CannedAccessControlList.Private);
        cosClient.putObject(putObjectRequest);
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest("test-1258463718", "/test.png");
        generatePresignedUrlRequest.setExpiration(DateUtil.offsetSecond(DateUtil.date(), 30));
        URL url = cosClient.generatePresignedUrl(generatePresignedUrlRequest);
        System.out.println(url.toString());
	}
	
}
