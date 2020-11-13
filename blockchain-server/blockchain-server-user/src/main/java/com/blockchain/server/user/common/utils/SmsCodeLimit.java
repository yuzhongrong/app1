package com.blockchain.server.user.common.utils;

import com.blockchain.common.base.util.HttpRequestUtil;
import com.blockchain.server.user.common.constants.other.RedisConstant;
import com.blockchain.server.user.common.enums.UserEnums;
import com.blockchain.server.user.common.exceprion.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class SmsCodeLimit {

    private static final Logger LOG = LoggerFactory.getLogger(SmsCodeLimit.class);

    @Autowired
    private RedisTemplate redisTemplate;

    //发送短信ip缓存key类型，格式后键名：user:sms:limit_set:{ipAddr}
    private static final String SMS_IP_LIMIT_KEY = "limit_set";
    //发送短信ip限制个数
    private static final int SMS_IP_LIMIT_NUM = 200;
    //发送短信ip限制缓存过期时间，小时
    private static final int SMS_IP_LIMIT_TIMEOUT = 1;

    //发送短信手机号码时间间隔缓存key类型，格式后键名：user:sms:time_lag:{phone}
    private static final String SMS_TIME_LAG_KEY = "time_lag";
    //发送短信手机号码时间间隔缓存过期时间，小时
    private static final int SMS_TIME_LAG_TIMEOUT = 1;


    /**
     * 校验获取短信验证码请求ip限制，相同ip一小时内只能5个手机号获取验证码
     */
    void verifyIpLimit(String phoneNum) {
        String ip = HttpRequestUtil.getIpAddr();
        LOG.info("=========== 校验获取短信验证码请求ip限制：请求ip={} ===========", ip);
        String smsLimitKey = RedisConstant.getSmsHashKey(SMS_IP_LIMIT_KEY, ip);
        Set<String> smsLimitIpSet = (HashSet<String>) redisTemplate.opsForValue().get(smsLimitKey);
        if (smsLimitIpSet == null) {
            smsLimitIpSet = new HashSet<>();
            smsLimitIpSet.add(phoneNum);
        } else if (smsLimitIpSet.contains(phoneNum)) {
            return;
        } else if (smsLimitIpSet.size() < SMS_IP_LIMIT_NUM) {
            smsLimitIpSet.add(phoneNum);
        } else {
            LOG.info("=========== 校验获取短信验证码请求ip限制：请求ip={}, 手机号={}, 该ip缓存手机集合={} ===========", ip, phoneNum, Arrays.toString(smsLimitIpSet.toArray()));
            throw new UserException(UserEnums.SMS_IP_LIMIT);
        }

        redisTemplate.opsForValue().set(smsLimitKey, smsLimitIpSet, SMS_IP_LIMIT_TIMEOUT, TimeUnit.HOURS);
    }

    /**
     * 校验相同手机号获取短信验证码时间间隔，1小时内随斐波那契数列增长
     *
     * @param phoneNum 手机号
     */
    void verifyTimeLag(String phoneNum) {
        long now = System.currentTimeMillis();
        String timeLagKey = RedisConstant.getSmsHashKey(SMS_TIME_LAG_KEY, phoneNum);
        Object[] timeLagArr = (Object[]) redisTemplate.opsForValue().get(timeLagKey);
        if (timeLagArr == null) {
            timeLagArr = new Object[2];
            timeLagArr[0] = 1;
            timeLagArr[1] = now;
        } else {
            int index = (int) timeLagArr[0];
            int timeLag = fibonacci(index);
            long lastTimestamp = (long) timeLagArr[1];

            if (now - lastTimestamp < timeLag * 10000 * 60000) {
                LOG.info("=========== 校验相同手机号获取短信验证码时间间隔：手机号={}, 请求次数={}, 应该间隔分钟数={}, 实际间隔秒数={} ===========", phoneNum, index, timeLag, (now - lastTimestamp) / 1000);
               throw new UserException(UserEnums.REQUEST_TOO_BUSY);
            } else {
                timeLagArr[0] = index + 1;
                timeLagArr[1] = now;
            }
        }

        redisTemplate.opsForValue().set(timeLagKey, timeLagArr, SMS_TIME_LAG_TIMEOUT, TimeUnit.HOURS);
    }

    /**
     * 获取斐波那契数列值
     *
     * @param index 下标，1开始
     * @return
     */
    private int fibonacci(int index) {
        if (index == 1 || index == 2) {
            return 1;
        } else {
            return fibonacci(index - 2) + fibonacci(index - 1);
        }
    }

}
