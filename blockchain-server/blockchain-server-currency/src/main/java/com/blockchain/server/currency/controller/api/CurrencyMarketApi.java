package com.blockchain.server.currency.controller.api;

public class CurrencyMarketApi {
    public static final String MARKET_CONTROLLER_API = "数字货币行情控制器";

    public static class Get {
        public static final String METHOD_API_NAME = "最新行情";
        public static final String METHOD_API_NOTE = "获取数字货币最新行情";
        public static final String METHOD_API_CURRENCY_PAIR = "数字货币对";
    }

    public static class GetLast {
        public static final String METHOD_API_NAME = "获取今天之前的最新行情";
        public static final String METHOD_API_NOTE = "获取今天之前的最新行情";
        public static final String METHOD_API_CURRENCY_PAIR = "数字货币对";
    }

    public static class GetRates {
        public static final String METHOD_API_NAME = "获取所有法币行情";
        public static final String METHOD_API_NOTE = "获取所有法币行情";
        public static final String METHOD_API_COIN = "法币";
    }

    public static class GetList {
        public static final String METHOD_API_NAME = "最新行情列表";
        public static final String METHOD_API_NOTE = "获取数字货币最新行情列表";
    }

    public static class GetHomeList {
        public static final String METHOD_API_NAME = "首页行情";
        public static final String METHOD_API_NOTE = "获取首页行情列表";
    }

    public static class GetTopList {
        public static final String METHOD_API_NAME = "涨跌幅榜单";
        public static final String METHOD_API_NOTE = "获取涨跌幅榜单行情列表";
    }

    public static class GetHistoryList {
        public static final String METHOD_API_NAME = "成交历史";
        public static final String METHOD_API_NOTE = "获取成交历史列表";
        public static final String METHOD_API_CURRENCY_PAIR = "数字货币对";
    }

    public static class GetQuoteList {
        public static final String METHOD_API_NAME = "获取主要货币的币对";
        public static final String METHOD_API_NOTE = "根据主要货币获取主要货币的币对";
        public static final String METHOD_API_CURRENCY_NAME = "数字货";
    }

    public static class Query {
        public static final String METHOD_API_NAME = "获取数字货币行情列表";
        public static final String METHOD_API_NOTE = "获取数字货币行情列表";
        public static final String METHOD_API_CURRENCY_PAIR = "数字货币对";
        public static final String METHOD_API_TIME_TYPE = "时间类型，MINUTE:分，HOUR:小时，DAY:天，MONTH:月,WEEK:周";
        public static final String METHOD_API_TIME_NUMBER = "时间数，如15分钟则为15";
        public static final String METHOD_API_START = "开始位置，默认0";
        public static final String METHOD_API_STOP = "结束位置，默认100，最大1000";
    }

    public static class QuerySortDesc {
        public static final String METHOD_API_NAME = "获取数字货币行情列表-排序倒序";
        public static final String METHOD_API_NOTE = "获取数字货币行情列表-排序倒序";
        public static final String METHOD_API_CURRENCY_PAIR = "数字货币对";
        public static final String METHOD_API_TIME_TYPE = "时间类型，MINUTE:分，HOUR:小时，DAY:天，MONTH:月,WEEK:周";
        public static final String METHOD_API_TIME_NUMBER = "时间数，如15分钟则为15";
        public static final String METHOD_API_START = "开始位置，默认0";
        public static final String METHOD_API_STOP = "结束位置，默认100，最大1000";
    }

}
