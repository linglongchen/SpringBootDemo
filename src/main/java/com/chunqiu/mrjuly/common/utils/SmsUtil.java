package com.chunqiu.mrjuly.common.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Description:
 * @Date: 2018/1/26
 * @Author: wcf
 */
public class SmsUtil {

    private static Logger logger = LoggerFactory.getLogger(SmsUtil.class);

    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    static final String accessKeyId = "";
    static final String accessKeySecret = "";


    public static boolean sendValidCodeSms(String tempNo, String phone, String param){
        return sendSmsByTemp(tempNo, phone, param);
    }

    /**
     * 通过模版发送消息
     * @param tempNo 模版编号
     * @param phone 手机号码
     * @param param 完整的模版参数
     * @return
     */
    private static boolean sendSmsByTemp(String tempNo, String phone, String param){
        try {
            //可自助调整超时时间
            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");

            //初始化acsClient,暂不支持region化
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);

            //组装请求对象-具体描述见控制台-文档部分内容
            SendSmsRequest request = new SendSmsRequest();
            //必填:待发送手机号
            request.setPhoneNumbers(phone);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName("");
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(tempNo);
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            request.setTemplateParam(param);

            //hint 此处可能会抛出异常，注意catch
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")){
                return true;
            }else {
                logger.error("发送短信失败！Code=" + sendSmsResponse.getCode() + "，Message=" + sendSmsResponse.getMessage());
            }
        }catch (ClientException e){
            e.printStackTrace();
        }
        return false;
    }


    public static void main(String[] args) {
//        sendValidCodeSms("15806292619", "356845");
//        sendValidCodeSms("15806292619","");
    }
}
