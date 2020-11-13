package com.blockchain.server.user.manager;

import com.alibaba.fastjson.JSONObject;
import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.common.base.util.IpUtils;
import com.blockchain.server.user.common.enums.UserEnums;
import com.blockchain.server.user.common.exceprion.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 腾讯滑块拼图验证码接口
 */
@Component
public class TencentCaptcha {
    private static final Logger LOG = LoggerFactory.getLogger(TencentCaptcha.class);

    @Autowired
    private RestTemplate restTemplate;

    private static final int SUCCESS_CODE = 1;
    private static final String APP_ID = "2088469240";
    private static final String APP_SECRET = "0NTC_xNdkT_JjU4YIpxctbA**";
    private static final String VERIFY_URI = "https://ssl.captcha.qq.com/ticket/verify?aid={aid}&AppSecretKey={AppSecretKey}&Ticket={Ticket}&Randstr={Randstr}&UserIP={UserIP}";


    /**
     * 滑块拼图验证码接口校验
     */
    public void verifyTicket(String ticket, String randstr, HttpServletRequest request) {
        ExceptionPreconditionUtils.checkStringNotBlank(ticket, new UserException(UserEnums.TENCENT_CAPTCHA_ERROR));
        ExceptionPreconditionUtils.checkStringNotBlank(randstr, new UserException(UserEnums.TENCENT_CAPTCHA_ERROR));

        try {
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("aid", APP_ID);
            paramMap.put("AppSecretKey", APP_SECRET);
            paramMap.put("Ticket", ticket);
            paramMap.put("Randstr", randstr);
            String userIp = IpUtils.getRequestRealIpAddr(request);
            paramMap.put("UserIP", userIp);
            LOG.info("*********** 腾讯滑块拼图验证码接口，参数={} ***********", JSONObject.toJSON(paramMap).toString());
            String resStr = restTemplate.getForObject(VERIFY_URI, String.class, paramMap);
            JSONObject json = JSONObject.parseObject(resStr);
            LOG.info("*********** 腾讯滑块拼图验证码接口，返回信息={} ***********", json.toJSONString());
            int code = json.getIntValue("response");
            if (SUCCESS_CODE != code) {
                throw new UserException(UserEnums.TENCENT_CAPTCHA_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new UserException(UserEnums.TENCENT_CAPTCHA_ERROR);
        }
    }

}
