package com.blockchain.server.user.common.constants.other;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 缓存到redis的key的规则信息
 * @author huangxl
 * @create 2019-02-23 15:45
 */
public class RedisConstant {
    private static final String SMS_HASH_KEY = "user:sms:{0}:{1}";//短信缓存值，第一个是短信类型，第二个是手机号
    private static final String FILE_UPLOAD_TIMES_KEY = "user:upload:times:{0}:{1}";//上传文件次数的值，第一个是用户id，第二个是日期格式yyyyMMdd

    private static final String EMAIL_CODE_KEY = "user:email:{0}";//绑定邮箱时存放验证码的key
    /**
     * 获取手机验证码的redis key
     *
     * @param mobilePhone 手机号
     * @param type        类型
     */
    public static String getSmsHashKey(String type, String mobilePhone) {
        return MessageFormat.format(SMS_HASH_KEY, type, mobilePhone);
    }

    /**
     * 获取文件上传次数的key
     * @param userId 用户id
     */
    public static String getFileUploadTimesKey(String userId){
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        return MessageFormat.format(FILE_UPLOAD_TIMES_KEY,userId,date);
    }

    /**
     * 获取绑定邮箱时缓存的key
     * @param email 邮箱
     */
    public static String getEmailCodeKey(String email) {
        return MessageFormat.format(EMAIL_CODE_KEY,email);
    }
}
