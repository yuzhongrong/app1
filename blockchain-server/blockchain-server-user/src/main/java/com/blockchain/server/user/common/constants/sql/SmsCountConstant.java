package com.blockchain.server.user.common.constants.sql;

/**
 * @author huangxl
 * @create 2019-02-23 16:50
 */
public class SmsCountConstant {
    public static final String SMS_TYPE_REGISTER = "REGISTER";
    public static final String SMS_TYPE_SET_PASSWORD = "UPDATE_PW";
    public static final String SMS_TYPE_UPDATE_ACCOUNT = "UPDATE_ACCOUNT";
    public static final String SMS_TYPE_LOGIN = "LOGIN";
    public static final String SMS_TYPE_FORGET_PASSWORD = "FORGET_PASSWORD";
    public static final String SMS_TYPE_CHANGE_WALLET_PASSWORD = "UPDATE_WALLET_PASSWORD";
    public static final String SMS_TYPE_BIND = "BIND";
    public static final String SMS_WITHDRAW = "WITHDRAW";

    //简体中文默认模板
    public static final String TEMPLETE_ZH_CN = "【DUOBIT】尊敬的用户您好！感谢您选择DUOBIT！验证码：{0}，验证码有效时间：5分钟。请勿向任何人包括客服提供验证码。";
    //繁体中文默认模板
    public static final String TEMPLETE_ZH_HK = "【DUOBIT】尊敬的用戶您好！感謝您選擇DUOBIT！驗證碼：{0}，驗證碼有效時間：5分鐘。請勿向任何人包括客服提供驗證碼。";
    //英文默认模板
    public static final String TEMPLETE_EN_US = "【DUOBIT】Dear user! Thank you for choosing DUOBIT! Verification Code: {0}, validity time of verification code: 5 minutes. Do not provide verification code to anyone including customer service.";
}
