package com.blockchain.server.user.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.blockchain.common.base.dto.NotifyOutSMS;
import com.blockchain.server.user.common.config.SmsUtils;
import com.blockchain.server.user.common.enums.SmsCountEnum;
import lombok.Data;
import org.apache.commons.collections.MapUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URI;
import java.text.MessageFormat;
import java.util.*;

/**
 * 短信API服务调用
 **/
@Data
@Configuration
@Component
@ConfigurationProperties(prefix = "chuanglansms")
public class SendSmsgCode {
    @Autowired
    private RestTemplate restTemplate;

    private static final Logger LOG = LoggerFactory.getLogger(SendSmsgCode.class);

    private boolean closed;//是否开启
    private long timeout = 5 ;//超时时间，单位：分钟

    //短信key

    public  static String apikey ="956f639bd74b193b476e4c354d1d81a0" ;

    //注册模板(中文)

    private  static final   String TEXTREGISTERED_ZH ="【DOBTmall】尊敬的{0}先生/女士，您的短信验证码为：{1}，请在15分钟内按照页面提示提交验证码，切勿将验证码告诉他人。谢谢！" ;

    //注册模板(繁体)
    private  static final   String TEXTREGISTERED_HK  ="【DOBTmall】尊敬的{0}先生/女士，您的短信验证码为：{1}，请在15分钟内按照页面提示提交验证码，切勿将验证码告诉他人。谢谢！";
    //注册模板(英文)
    private static final   String REGISTERED_ENGLISH ="[DUOBIT] dear {0} users, Thank you for choosing DUOBIT! Verification code: {1}, verification code valid time: 5 minutes. Please do not provide verification code to anyone, including customer service.";
     //提现
    public  static final    String withdrawal = "【DUOBIT】 通知：账户 {0} 提 {1} {2}。请您及时查看。\"";
    private static final   String REGISTERED_ENGLISH_TAIWANG  =   "【DUOBIT】尊敬的用戶您好！您正在獲取安全驗證碼：{0}，驗證碼有效時間：5分鐘。請勿向任何人包括客服提供驗證碼。";

    //身份认证通知模板
    public static final  String AUTHENTICATION = "【DUOBIT】 DUOBIT 通知：账户 {0} 实名认证{1} {2}。请您及时查看。";

    //参数
    private String url;
    private String account;
    private String password;
    /* private String MSG_ZH_CN;
     private String MSG_ZH_HK;
     private String MSG_EN_US;*/
    //提现通知短信模板  【DUOBIT】提现通知：账户 17633333333 提现 100 BTC。请您及时查看。
    private String MSG_OUT;

    private String CHINA_CODE = "86";
    private List<String> BIG5_CODE = Arrays.asList(new String[]{"852", "853", "886"});

    /**
     * 请求短信接口，发送短信验证码
     *
     * @param mobile  手机号
     * @param smsCode 短信验证码
     * @return
     */
    @Async
    public void sendSmsg(String mobile, String smsCode, String internationalCode, SmsCountEnum smsCountEnum) {
        if (closed) {//如果没有开启短信，则不发送短信
            System.out.println("如果没有开启短信，则不发送短信---------"+mobile);
            return;
        }

/*        Map<String, String> params = new HashMap<>();
        params.put("account", account);
        params.put("password", password);
        params.put("mobile", internationalCode + mobile);

        //判断国际代码
        String msg;
        if (CHINA_CODE.equals(internationalCode)) {
            msg = MessageFormat.format(smsCountEnum.getTempleteZhCn(), smsCode);
        } else if (BIG5_CODE.contains(internationalCode)) {
            msg = MessageFormat.format(smsCountEnum.getTempleteZhHk(), smsCode);
        } else {
            msg = MessageFormat.format(smsCountEnum.getTempleteEnUs(), smsCode);
        }
        params.put("msg", msg);

        String res = restTemplate.postForObject(url, params, String.class);
        LOG.info("===send sms end==== : {} ", res);
        */
        cloudsRegistered(mobile,smsCode,internationalCode);
    }

    /**
     * 用户提现短信通知
     *
     * @param mobile       通知手机号
     * @param username     提现用户名
     * @param notifyOutSMS 提现参数
     */
    @Async
    public void notifyOut(String mobile, String username, NotifyOutSMS notifyOutSMS) {
/*        Map<String, String> params = new HashMap<>();
        params.put("account", account);
        params.put("password", password);
        params.put("mobile", mobile);
        String msg = MessageFormat.format(MSG_OUT, username, notifyOutSMS.getAmount(), notifyOutSMS.getCoin());
        params.put("msg", msg);

        String res = restTemplate.postForObject(url, params, String.class);
        LOG.info("===send sms end==== : {} ", res);*/

        try {
            String text = MessageFormat.format(withdrawal, username, notifyOutSMS.getAmount(), notifyOutSMS.getCoin());
            LOG.info("提现发送模板：" + text);
           // SmsUtils.send(text,mobile);
            //JavaSmsApi.sendSms(apikey, text, mobile);
        } catch (Exception e) {
            LOG.error(mobile + "提现发送验证码失败，手机号码：", e);
        }

    }

    /**
     * 请求短信接口，发送短信验证码
     *
     * @param mobile  手机号
     * @param smsCode 短信验证码
     * @param internationalCode 短信类型（中文，英文）
     * @return
     */
    @Async
   public void cloudsRegistered(String mobile, String smsCode,String internationalCode){

        try {
            //判断国际代码
            String text;
            if (CHINA_CODE.equals(internationalCode)) {//中文
                text = MessageFormat.format(TEXTREGISTERED_ZH, mobile,smsCode);
            } else if (BIG5_CODE.contains(internationalCode)) {//繁体
                text = MessageFormat.format(TEXTREGISTERED_HK, mobile,smsCode);
            } else {//英文
                text = MessageFormat.format(REGISTERED_ENGLISH, mobile,smsCode);
            }
            LOG.info("发送模板：" + text +" mobile:"+mobile);
            mobile = "+" + internationalCode + mobile;
           // JavaSmsApi.sendSms(apikey, text, mobile);
             SmsUtils.send(text,mobile);
        }catch (Exception e){
            LOG.error("发送验证码失败，手机号码：",mobile,e);
        }


   }


/*

    public static void main(String[] args) throws  Exception{

        // String   text = MessageFormat.format(withdrawal, "15502634295","500","USDT");;
       String   text = MessageFormat.format(withdrawal, "-贡献-","示，","失败！");

        System.out.println(text);
        JavaSmsApi.sendSms(apikey, text, "+8613128902339");
    }
*/




}