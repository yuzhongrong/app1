package com.blockchain.server.user.common.utils;

import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.user.common.constants.other.RedisConstant;
import com.blockchain.server.user.common.enums.SmsCountEnum;
import com.blockchain.server.user.common.enums.UserEnums;
import com.blockchain.server.user.common.exceprion.UserException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class EmailCodeUtils {


    @Autowired
    private EMailTransmitHelper eMailTransmitHelper;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 发送短信并保存到缓存中
     *
     * @param email        邮箱
     * @param smsCountEnum 枚举类型
     */
    public void sendSmsCodeAndStoreToCache(String email,String userLocale, SmsCountEnum smsCountEnum) {
        ExceptionPreconditionUtils.notEmpty(email);  //检查参数是否为空
        String key = RedisConstant.getSmsHashKey(smsCountEnum.getKey(),email);
        //判断是否存在这个key
        Object exist = redisTemplate.opsForValue().get(key);
        if (exist != null) {
            Long expire = redisTemplate.getExpire(key);
            //判断剩余超时时间是否已经过了一半，如果还剩余超过一半的时间，则不发送验证码
            if (expire != null && expire != -1 && ((expire / 1000 / 60) >= (eMailTransmitHelper.getTimeout() / 2))) {
                throw new UserException(UserEnums.SEND_CODE_INTERVAL_ERROR);

            }
        }
        String code;
        if(!eMailTransmitHelper.isClosed()){
            code = RandomStringUtils.random(6, false, true);
            redisTemplate.opsForValue().set(RedisConstant.getSmsHashKey(smsCountEnum.getKey(),email),code,eMailTransmitHelper.getTimeout(), TimeUnit.MINUTES);
            eMailTransmitHelper.sendEmail(email,code,userLocale,key);
        }
    }

    /** 
    * @Description: 验证邮箱与验证码
    * @Param: [verifyCode, emailaddress] 
    * @return: void 
    * @Author: Liu.sd 
    * @Date: 2019/6/26 
    */ 
    public void validateVerifyCode(String verifyCode, String emailaddress, SmsCountEnum smsCountEnum) {
        if(eMailTransmitHelper.isClosed()){
            return;
        }
        ExceptionPreconditionUtils.notEmpty(verifyCode, emailaddress);  //检查参数是否为空
        String key = RedisConstant.getSmsHashKey(smsCountEnum.getKey(),emailaddress);
        if(!redisTemplate.hasKey(key)){
            throw new UserException(UserEnums.VERIFY_CODE_DID_NOT_FIND);
        }
        String code = (String) redisTemplate.opsForValue().get(key);
        if(!verifyCode.equals(code)){
            throw new UserException(UserEnums.VERIFY_CODE_DID_NOT_MATCH);
        }
    }
}
