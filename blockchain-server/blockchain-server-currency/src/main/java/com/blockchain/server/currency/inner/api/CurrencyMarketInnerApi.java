package com.blockchain.server.currency.inner.api;

public class CurrencyMarketInnerApi {
    public static final String MARKET_CONTROLLER_API = "内部数字货币行情控制器";

    public static class Save{
        public static final String METHOD_API_NAME = "保存数字货币最新行情";
        public static final String METHOD_API_NOTE = "保存数字货币最新行情";
        public static final String METHOD_API_COIN_NAME = "币种名称";
        public static final String METHOD_API_UNIT_NAME="币种单位";
        public static final String METHOD_API_AMOUNT = "成交价格";
        public static final String METHOD_API_TOTAL = "成交数量";
        public static final String METHOD_API_TIMESTAMP = "成交时间";
        public static final String METHOD_API_TRADINGTYPE = "成交类型";
    }

}
