package com.blockchain.server.user.common.enums;

import com.blockchain.server.user.common.constants.sql.SmsCountConstant;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum SmsCountEnum {

    //单天最多收10条注册短信
    SMS_COUNT_REGISTER(SmsCountConstant.SMS_TYPE_REGISTER, 10, "register",
            SmsCountConstant.TEMPLETE_ZH_CN,
            SmsCountConstant.TEMPLETE_ZH_HK,
            SmsCountConstant.TEMPLETE_EN_US),
    //设置密码验证码
    SMS_COUNT_SET_PASSWORD(SmsCountConstant.SMS_TYPE_SET_PASSWORD, 10, "setPassword",
            SmsCountConstant.TEMPLETE_ZH_CN,
            SmsCountConstant.TEMPLETE_ZH_HK,
            SmsCountConstant.TEMPLETE_EN_US),
    //更新账号信息，修改手机号（用户名）
    SMS_COUNT_UPDATE_ACCOUNT(SmsCountConstant.SMS_TYPE_UPDATE_ACCOUNT, 10, "updateAccount",
            SmsCountConstant.TEMPLETE_ZH_CN,
            SmsCountConstant.TEMPLETE_ZH_HK,
            SmsCountConstant.TEMPLETE_EN_US),
    //登陆，短信验证码
    SMS_COUNT_LOGIN(SmsCountConstant.SMS_TYPE_LOGIN, 10, "login",
            SmsCountConstant.TEMPLETE_ZH_CN,
            SmsCountConstant.TEMPLETE_ZH_HK,
            SmsCountConstant.TEMPLETE_EN_US),
    //找回密码验证码
    SMS_COUNT_FORGET_PASSWORD(SmsCountConstant.SMS_TYPE_FORGET_PASSWORD, 10, "forgetPassword",
            "【DUOBIT】尊敬的用户您好！您正在重设登录密码。验证码：{0}，验证码有效时间：5分钟。请勿向任何人包括客服提供验证码。",
            "【DUOBIT】尊敬的用戶您好！您正在重設登錄密碼。驗證碼：{0}，驗證碼有效時間：5分鐘。請勿向任何人包括客服提供驗證碼。",
            "【DUOBIT】Dear user! You are resetting your login password. Verification code: {0}, it's valid time: 5 minutes. Do not provide verification code to anyone including customer service."),
    //重置、修改资金密码
    SMS_COUNT_UPDATE_WALLET_PASSWORD(SmsCountConstant.SMS_TYPE_CHANGE_WALLET_PASSWORD, 10, "changeWalletPassword",
            "【DUOBIT】尊敬的用户您好！您正在重设安全密码。验证码：{0}，验证码有效时间：5分钟。请勿向任何人包括客服提供验证码。",
            "【DUOBIT】尊敬的用戶您好！您正在重設安全密碼。驗證碼：{0}，驗證碼有效時間：5分鐘。請勿向任何人包括客服提供驗證碼。",
            "【DUOBIT】Dear user! You are resetting your security password. Verification code: {0}, it's valid time: 5 minutes. Do not provide verification code to anyone including customer service."),
    //绑定
    SMS_COUNT_BIND(SmsCountConstant.SMS_TYPE_BIND, 10, "bind",
            SmsCountConstant.TEMPLETE_ZH_CN,
            SmsCountConstant.TEMPLETE_ZH_HK,
            SmsCountConstant.TEMPLETE_EN_US),
    //提现
    SMS_WITHDRAW(SmsCountConstant.SMS_WITHDRAW, 10, "withdraw",
            "【DUOBIT】尊敬的用户您好！您正在获取安全验证码：{0}，验证码有效时间：5分钟。请勿向任何人包括客服提供验证码。",
            "【DUOBIT】尊敬的用戶您好！您正在獲取安全驗證碼：{0}，驗證碼有效時間：5分鐘。請勿向任何人包括客服提供驗證碼。",
            "【DUOBIT】Dear user! You are obtaining your security verification code. Verification code: {0}, it's valid time: 5 minutes. Do not provide verification code to anyone including customer service."),
    ;


    //类型,用于保存到数据库
    @Getter
    private String type;

    //每天最大短信
    @Getter
    private int maxCount;

    //存到缓存的key标识
    @Getter
    private String key;

    //短信模板中文
    @Getter
    private String templeteZhCn;

    //短信模板中文繁体
    @Getter
    private String templeteZhHk;

    //短信模板英文
    @Getter
    private String templeteEnUs;

    SmsCountEnum(String type, int maxCount, String key, String templeteZhCn, String templeteZhHk, String templeteEnUs) {
        this.type = type;
        this.maxCount = maxCount;
        this.key = key;
        this.templeteZhCn = templeteZhCn;
        this.templeteZhHk = templeteZhHk;
        this.templeteEnUs = templeteEnUs;
    }

    private static final Map<String, SmsCountEnum> map = new HashMap<>();

    static {
        for (SmsCountEnum smsCountEnum : values()) {
            map.put(smsCountEnum.type, smsCountEnum);
        }
    }

}
