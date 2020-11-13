package com.blockchain.server.user.common.utils;

import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.user.common.constants.other.RedisConstant;
import com.blockchain.server.user.common.constants.sql.SmsCountConstant;
import com.blockchain.server.user.common.enums.SmsCountEnum;
import com.blockchain.server.user.common.enums.UserEnums;
import com.blockchain.server.user.common.exceprion.UserException;
import com.blockchain.server.user.service.SmsCountService;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class SmsCodeUtils {
    private static final Logger LOG = LoggerFactory.getLogger(SmsCodeUtils.class);

    @Autowired
    private SendSmsgCode sendSmsgCode;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private EmailCodeUtils emailCodeUtils;
    @Autowired
    private SmsCodeLimit smsCodeLimit;
    @Value("${chuanglansms.status}")
    private String  smsStatus ;

    /**
     * 发送短信并保存到缓存中
     *
     * @param phone        手机号
     * @param smsCountEnum 枚举类型
     */
    public void sendSmsCodeAndStoreToCache(String phone, String internationalCode, SmsCountEnum smsCountEnum) {
        ExceptionPreconditionUtils.notEmpty(phone);  //检查参数是否为空
        if(!checkIsStatus())
            return;
        //校验获取短信验证码请求ip限制、及获取时间间隔
        //smsCodeLimit.verifyIpLimit(phone);
        //smsCodeLimit.verifyTimeLag(phone);

        String code = "666666";
        if (!sendSmsgCode.isClosed()) {
            code = RandomStringUtils.random(6, false, true);
        }
        redisTemplate.opsForValue().set(RedisConstant.getSmsHashKey(smsCountEnum.getKey(), phone), code, sendSmsgCode.getTimeout(), TimeUnit.MINUTES);
        sendSmsgCode.sendSmsg(phone, code, internationalCode, smsCountEnum);
    }

    /**
     * 验证手机号和验证码
     *
     * @param verifyCode   输入的验证码信息
     * @param phoneNum     手机号
     * @param smsCountEnum 枚举
     */
    public void validateVerifyCode(String verifyCode, String phoneNum, SmsCountEnum smsCountEnum) {

        if(!checkIsStatus())
            return;
        if (CheckUtils.checkEmail(phoneNum)) {
            emailCodeUtils.validateVerifyCode(verifyCode, phoneNum, smsCountEnum);
            return;
        }
        if (sendSmsgCode.isClosed()) {
            return;
        }
        ExceptionPreconditionUtils.notEmpty(verifyCode, phoneNum, smsCountEnum);  //检查参数是否为空
        String redisKey = RedisConstant.getSmsHashKey(smsCountEnum.getKey(), phoneNum);
        if (!redisTemplate.hasKey(redisKey)) {
            //转小写
            redisKey = redisKey  .toLowerCase();
            if(!redisTemplate.hasKey(redisKey)){
                LOG.info(redisKey+"手机获取不到验证码："+phoneNum+",verifyCode:"+verifyCode);
                throw new UserException(UserEnums.SMS_CODE_NOT_EXIST);
            }
        }
        String code = (String) redisTemplate.opsForValue().get(redisKey);
        if (!verifyCode.equals(code)) {
            throw new UserException(UserEnums.SMS_VERIFY_FAIL);
        }

        if( SmsCountEnum.SMS_WITHDRAW.getType().equals(smsCountEnum.getType()) ||  SmsCountEnum.SMS_WITHDRAW.getType().equals(smsCountEnum.getType().toLowerCase())){
            LOG.info("删除(提现)手机验证码："+phoneNum+",verifyCode:"+verifyCode);
            //成功则del短信信息
            this.removeKey(phoneNum,smsCountEnum);
        }



    }
    private boolean checkIsStatus(){
        LOG.info("smsStatus is:"+smsStatus);
        if(smsStatus==null ||smsStatus.equals("")||smsStatus.equals("false")){
            return false;
        }else{
            return true;
        }
    }


    /**
     * 删除缓存的key
     *
     * @param phoneNum     手机号
     * @param smsCountEnum 验证码枚举类型
     */
    public void removeKey(String phoneNum, SmsCountEnum smsCountEnum) {
        String redisKey = RedisConstant.getSmsHashKey(smsCountEnum.getKey(), phoneNum);
        //验证完成，删除缓存信息

        redisTemplate.delete(redisKey);
    }
}
