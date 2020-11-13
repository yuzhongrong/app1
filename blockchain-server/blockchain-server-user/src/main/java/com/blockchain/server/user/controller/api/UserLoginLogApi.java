package com.blockchain.server.user.controller.api;

/**
 * @author Harvey
 * @date 2019/3/5 15:26
 * @user WIN10
 */
public class UserLoginLogApi {
    public static final String USER_LOGIN_LOG_API = "登录日志控制器";

    public static final String METHOD_API_PAGE_NUM = "当前页数";
    public static final String METHOD_API_PAGE_SIZE = "当前页面内容";

    public static class ListUserLoginLog {
        public static final String MATHOD_API_NAME = "查询用户登录日志";
        public static final String MATHOD_API_NOTE = "查询用户登录日志";

        public static final String MATHOD_API_USER_ID = "用户id";
    }

}
