package com.blockchain.server.tron.inner.api;

public class WalletPayApi {
    public static final String MARKET_CONTROLLER_API = "支付接口控制器";

    public static class Rsacode {
        public static final String METHOD_API_NAME = "获取密码加密公钥";
        public static final String METHOD_API_NOTE = "获取密码加密公钥";
        public static final String METHOD_API_PREPAYID = "预支付ID";
    }

    public static class Order {
        public static final String METHOD_API_NAME = "下单接口";
        public static final String METHOD_API_NOTE = "下单接口";
        public static final String METHOD_API_INPARAMS = "参数数据对象";
    }

    public static class Pay {
        public static final String METHOD_API_NAME = "支付接口";
        public static final String METHOD_API_NOTE = "支付接口";
        public static final String METHOD_API_PASSWORD = "密码";
        public static final String METHOD_API_PREPAYID = "预支付ID";
    }

    public static class Refund {
        public static final String METHOD_API_NAME = "退款接口";
        public static final String METHOD_API_NOTE = "退款接口";
        public static final String METHOD_API_INPARAMS = "参数据对象";
    }

    public static class QueryOrder {
        public static final String METHOD_API_NAME = "订单查询接口";
        public static final String METHOD_API_NOTE = "订单查询接口";
        public static final String METHOD_API_APPID = "应用id";
        public static final String METHOD_API_APPSECRET = "应用安全码";
        public static final String METHOD_API_TRADENO = "商户单号";
    }

    public static class QueryRefund {
        public static final String METHOD_API_NAME = "退款查询接口";
        public static final String METHOD_API_NOTE = "退款查询接口";
        public static final String METHOD_API_APPID = "应用id";
        public static final String METHOD_API_APPSECRET = "应用安全码";
        public static final String METHOD_API_TRADENO = "商户单号";
    }

    public static class CheckToken {
        public static final String METHOD_API_NAME = "请求用户数据";
        public static final String METHOD_API_NOTE = "请求用户数据";
        public static final String METHOD_API_APPID = "应用id";
        public static final String METHOD_API_APPSECRET = "应用安全码";
    }

    public static class Recharge {
        public static final String METHOD_API_NAME = "发放奖金接口";
        public static final String METHOD_API_NOTE = "发放奖金接口";
        public static final String METHOD_API_INPARAMS = "参数据对象";
    }

    public static class QueryRecharge {
        public static final String METHOD_API_NAME = "奖金发放查询接口";
        public static final String METHOD_API_NOTE = "奖金发放查询接口";
        public static final String METHOD_API_APPID = "应用id";
        public static final String METHOD_API_APPSECRET = "应用安全码";
        public static final String METHOD_API_TRADENO = "商户单号";
    }
}
