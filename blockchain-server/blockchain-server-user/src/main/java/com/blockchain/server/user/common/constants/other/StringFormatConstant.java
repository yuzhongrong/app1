package com.blockchain.server.user.common.constants.other;

import java.text.MessageFormat;

/**
 * 存放一些字符串格式的类
 *
 * @author huangxl
 * @create 2019-02-24 11:06
 */
public class StringFormatConstant {
    private static final String USER_CHANGE_ACCOUNT_FORMAT = "用户【{0}】修改手机号，手机号「{1}」-->「{2}」,国际标识号「{3}」-->「{4}」";
    private static final String USER_BIND_GOOGLE_AUTHENTICATOR = "用户【{0}】绑定了谷歌验证器";
    private static final String USER_BIND_EMAIL = "用户【{0}】绑定了邮箱";
    private static final String USER_BIND_LOGIN_PASSWORD = "用户【{0}】首次设置了登录密码";
    private static final String USER_CHANGE_LOGIN_PASSWORD = "用户【{0}】修改了登录密码";

    public static String getUserChangeAccountFormat(String userId, String oldTel, String newTel, String oldCode, String newCode) {
        return MessageFormat.format(USER_CHANGE_ACCOUNT_FORMAT, userId, oldTel, newTel, oldCode, newCode);
    }

    public static String getUserBindGoogleAuthenticator(String userId) {
        return MessageFormat.format(USER_BIND_GOOGLE_AUTHENTICATOR, userId);
    }

    public static String getUserBindEmail(String userId) {
        return MessageFormat.format(USER_BIND_EMAIL, userId);
    }

    public static String getUserBindLoginPassword(String userId) {
        return MessageFormat.format(USER_BIND_LOGIN_PASSWORD, userId);
    }

    public static String getUserChangeLoginPassword(String userId) {
        return MessageFormat.format(USER_CHANGE_LOGIN_PASSWORD, userId);
    }
}
