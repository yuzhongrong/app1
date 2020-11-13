package com.blockchain.server.currency.controller.api;

public class CurrencyApi {
    public static final String MARKET_CONTROLLER_API = "数字货币控制器";

    public static class GetUsableList{
        public static final String METHOD_API_NAME = "获取可用数字货币对";
        public static final String METHOD_API_NOTE = "获取可用数字货币对";
    }


    public static class GetMyToken{
        public static final String METHOD_API_NAME = "获取可用MyToken";
        public static final String METHOD_API_NOTE = "获取可用MyToken";
    }
    public static class GetCurrencyInfo{
        public static final String METHOD_API_NAME = "获取数字货币详情";
        public static final String METHOD_API_NOTE = "获取数字货币详情";
        public static final String METHOD_API_CURRENCY_NAME = "货币名称";
    }

    public static class GetQuoteCurrency{
        public static final String METHOD_API_NAME = "主要数字货币";
        public static final String METHOD_API_NOTE = "获取主要数字货币";
    }

    public static class List{
        public static final String METHOD_API_NAME = "主要数字货币";
        public static final String METHOD_API_NOTE = "获取主要数字货币";
    }

}
