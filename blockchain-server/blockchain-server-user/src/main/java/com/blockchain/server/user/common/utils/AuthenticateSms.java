package com.blockchain.server.user.common.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blockchain.server.user.common.enums.UserEnums;
import com.blockchain.server.user.common.exceprion.UserException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Datetime: 2020/5/15   15:19
 * @Author: Xia rong tao
 * @title   短信行为验证层
 */

public class AuthenticateSms {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticateSms.class);

    //短信行为验证接口
    private final static String AUTH_URL = "https://captcha.yunpian.com/v1/api/authenticate";
    //验证应用 id，对应用户后台分配的 APPID
    private final static String CAPTCHA_ID = "38ad1a3d09034e6ab26564b3f7189627";
    //验证应用密钥 id
    private final static String SECRET_ID = "7b4f5c837da2452fbf8c00d8386d8252";
    //签名信息，见签名计算方法
    private final static String SIGNATURE = "c5a007f664844e59a7c616ded9a236c5";

    //版本，固定值 1.0
    private final static String VERSION = "1.0";
    //行为验证结果
    private final static String INSPECT = "0";

    /**
     * 响应码	错误信息	具体描述
     * 0	ok	二次验证通过
     * 400	validate_fail	二次验证不通过
     * 400	signature_invalid	签名校验失败
     * 400	param_invalid	请求参数错误，检查 i k 参数
     * 400	captcha_id_invalid	APPID 不存在
     * 429	too many requests	请求过于频繁，请稍后再试
     * 500	server_error	服务异常
     * 详情参考文档 ：https://www.yunpian.com/official/document/sms/zh_cn/captcha_captcha_service_h5
     */

    /**
     * 短信行为验证
     * @param token
     * @param authenticate
     * @param phone
     */
    public static boolean  smsgBehaviorInspect (String token ,String authenticate ,String phone) {

        if(  StringUtils.isEmpty(token) ||  StringUtils.isEmpty(authenticate)  ){
            return false ;

        }
        Map<String, String> paramMap = new HashMap<>();
        //验证应用 id，对应用户后台分配的 APPID
        paramMap.put("captchaId", CAPTCHA_ID);
        //验证应用密钥 id
        paramMap.put("secretId", SECRET_ID);
        //前端从 verfiy 接口获取的 token，token 作为一次验证的标志。
        paramMap.put("token", token);
        //前端从 verfiy 接口验证通过后，返回的参数
        paramMap.put("authenticate", authenticate);
        paramMap.put("version", VERSION);
        paramMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
        //随机正整数, 在 1-99999 之间，与 timestamp 配合可以防止消息重放
        paramMap.put("nonce", String.valueOf(new Random().nextInt(99999)));
        //可选值，接入方用户标志，如担心信息泄露，可采用摘要方式给出。
        paramMap.put("user", phone);

        String signature = genSignature(SIGNATURE, paramMap);
        paramMap.put("signature", signature);
        LOG.info(phone + "向第三方发送信息：" + JSON.toJSONString(paramMap));
        StringBuilder sb = new StringBuilder();
        PostMethod postMethod = new PostMethod(AUTH_URL);
        postMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        paramMap.forEach((k, v) -> {
            postMethod.addParameter(k, v);
        });
        HttpClient httpClient = new HttpClient();
        String responseBodyAsString = null;
        try {
            int status = httpClient.executeMethod(postMethod);
            responseBodyAsString = postMethod.getResponseBodyAsString();
        } catch (Exception ex) {
            LOG.error("短信行为验证请求异常", phone, ex);
        }


        JSONObject json = JSON.parseObject(responseBodyAsString);
        LOG.info(phone + "第三方返回信息：" + json.toJSONString());
        if (!INSPECT.equals(json.get("code").toString())) {
            //行为验证失败抛出异常
          return false ;

        }

        return true ;
    }


    private static String genSignature(String secretKey, Map<String, String> params) {
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            sb.append(key).append(params.get(key));
        }
        sb.append(secretKey);
        return DigestUtils.md5Hex(sb.toString());
    }
}
