package com.blockchain.server.tron.controller.api;

/**
 * @author Harvey
 * @date 2019/3/23 11:33
 */
public class TronWalletPayApi {
    public static final String MARKET_CONTROLLER_API = "支付接口控制器";

    public static class Pay {
        public static final String METHOD_API_NAME = "支付接口";
        public static final String METHOD_API_NOTE = "支付接口";
        public static final String METHOD_API_PASSWORD = "密码";
        public static final String METHOD_API_PREPAYID = "预支付ID";
    }



    public static class CheckToken {
        public static final String METHOD_API_NAME = "请求用户数据";
        public static final String METHOD_API_NOTE = "请求用户数据";
        public static final String METHOD_API_APPID = "应用id";
        public static final String METHOD_API_APPSECRET = "应用安全码";
    }
}
