package work.metanet.utils;

import java.io.File;
import java.net.URL;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.GeneratePresignedUrlRequest;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;

public class AppendObjectDemo {
	
    public static void AppendObjectFromLocal() {
        COSCredentials cred = new BasicCOSCredentials("AKIDKSLynMdpjOtBB0r5II6f05xgDcppO5OS", "zkG8oFTRB0iCNM6aooa7SWNzMSM0PQAh");
        ClientConfig clientConfig = new ClientConfig(new Region("ap-guangzhou"));
        String bucketName = "ailoxi-devpv-1251242904";
        COSClient cosClient = new COSClient(cred, clientConfig);
        try {
        	String key = "books/RJB_3A/cover.jpg";
            File localFile = new File("F:/books/RJB_3A/cover.jpg");
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
            cosClient.putObject(putObjectRequest);
            URL url = cosClient.generatePresignedUrl(new GeneratePresignedUrlRequest(bucketName, key, HttpMethodName.GET));
            System.out.println(url.toString());
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }
        // 关闭客户端
        cosClient.shutdown();
    }

    public static void main(String[] args) throws Exception{
    	AppendObjectFromLocal();
    }
}