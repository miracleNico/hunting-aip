package work.metanet.util;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import work.metanet.constant.ConstSmsRequestType;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
// 导入 SMS 模块的 client
import com.tencentcloudapi.sms.v20190711.SmsClient;
// 导入要请求接口对应的 request response 类
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Tencent Cloud Sms Sendsms
 * https://cloud.tencent.com/document/product/382/38778
 */
@Slf4j
@Component
public class SmsUtil{
	
	@Value("${tencent.cloud.accessKey:}")
	private String accessKey;
	@Value("${tencent.cloud.secretKey:}")
	private String secretKey;
	@Value("${tencent.cloud.sms.appid:}")
	private String appid;
	@Value("${tencent.cloud.sms.region:}")
	private String region;
	private String sign = "METANET";
	
	private SendSmsResponse sendSms(String sign,String templateID,String[] templateParams,String[] phoneNumbers) {
		SendSmsResponse res = null;
		try {
            //实例化一个认证对象
            Credential cred = new Credential(accessKey, secretKey);
            //实例化 SMS 的 client 对象,第二个参数是地域信息，可以直接填写字符串 ap-guangzhou，或者引用预设的常量 
            SmsClient client = new SmsClient(cred, region);
            //实例化一个请求对象
            SendSmsRequest req = new SendSmsRequest();
            //短信应用 ID: 在 [短信控制台] 添加应用后生成的实际 SDKAppID，例如1400006666 
            req.setSmsSdkAppid(appid);
            //短信签名内容: 使用 UTF-8 编码，必须填写已审核通过的签名，可登录 [短信控制台] 查看签名信息 
            req.setSign(sign);
            //模板id
            req.setTemplateID(templateID);
            //电话号码
            req.setPhoneNumberSet(phoneNumbers);
            //模板参数: 若无模板参数，则设置为空
            req.setTemplateParamSet(templateParams);
            //发送短信并返回信息
            res = client.SendSms(req);
            //输出 JSON 格式的字符串回包
            //System.out.println(SendSmsResponse.toJsonString(res));
            //可以取出单个值，您可以通过官网接口文档或跳转到 response 对象的定义处查看返回字段的定义
            //System.out.println(res.getRequestId());
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
            log.error(ExceptionUtils.getStackTrace(e));
        }
		log.info("---------------sms.send->"+SendSmsResponse.toJsonString(res));
		return res;
	}
	
	@Async
	public void sendSms(String sign, ConstSmsRequestType type,String phones,String value) {
		String[] templateParams = {value};
		String[] phoneNumbers = {"+86"+phones};
		sendSms(StrUtil.emptyToDefault(sign, this.sign), type.getTemplateID(), templateParams, phoneNumbers);
	}
	
	@Async
	public void sendSmsWarning(String phones,String produce,String time,String errmsg) {
		String[] templateParams = {produce,time,errmsg};
		String[] phoneNumbers = {"+86"+phones};
		sendSms(this.sign, ConstSmsRequestType.WARNING.getTemplateID(), templateParams, phoneNumbers);
	}
	
	/**
    public static void main( String[] args )
    {
        try {
            //实例化一个认证对象
            Credential cred = new Credential("AKID2OZcTBcLxUze5Kf4OUKwTqjRtJUPfTcV", "nYSChvue8MJB3g28B7sS1KNBpDLCnIad");
            //实例化 SMS 的 client 对象,第二个参数是地域信息，可以直接填写字符串 ap-guangzhou，或者引用预设的常量 
            SmsClient client = new SmsClient(cred, "ap-guangzhou");
            //实例化一个请求对象
            SendSmsRequest req = new SendSmsRequest();
            //短信应用 ID: 在 [短信控制台] 添加应用后生成的实际 SDKAppID，例如1400006666 
            String appid = "1400384061";
            req.setSmsSdkAppid(appid);
            //短信签名内容: 使用 UTF-8 编码，必须填写已审核通过的签名，可登录 [短信控制台] 查看签名信息 
            String sign = "METANET";
            req.setSign(sign);
            //模板id
            String templateID = "630893";
            req.setTemplateID(templateID);
            //电话号码
            String[] phoneNumbers = {"+8618316588439"};
            req.setPhoneNumberSet(phoneNumbers);
            //模板参数: 若无模板参数，则设置为空
            String[] templateParams = {"5678"};
            req.setTemplateParamSet(templateParams);
            //发送短信并返回信息
            SendSmsResponse res = client.SendSms(req);
            //输出 JSON 格式的字符串回包
            System.out.println(SendSmsResponse.toJsonString(res));
            //可以取出单个值，您可以通过官网接口文档或跳转到 response 对象的定义处查看返回字段的定义
            System.out.println(res.getRequestId());
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
    }
    */
} 