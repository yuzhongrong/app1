package com.blockchain.server.cct.controller.api;

public class TradingRecordApi {
    public static final String RECORD_API = "成交记录控制器";

    public static class listUserHistory {
        public static final String METHOD_TITLE_NAME = "查询用户成交记录";
        public static final String METHOD_TITLE_NOTE = "根据条件查询用户成交记录";
        public static final String METHOD_API_COINNAME = "基本货币";
        public static final String METHOD_API_UNITNAME = "二级货币";
        public static final String METHOD_API_BEGINTIME = "开始时间";
        public static final String METHOD_API_LASTTIME = "结束时间";
        public static final String METHOD_API_STATUS = "订单状态";
        public static final String METHOD_API_PAGENUM = "页码";
        public static final String METHOD_API_PAGESIZE = "每页条数";
    }

    public static class listRecordByCoinAndUnit {
        public static final String METHOD_TITLE_NAME = "根据交易币对查询成交记录";
        public static final String METHOD_TITLE_NOTE = "根据交易币对查询成交记录";
        public static final String METHOD_API_COINNAME = "基本货币";
        public static final String METHOD_API_UNITNAME = "二级货币";
        public static final String METHOD_API_PAGENUM = "页码";
        public static final String METHOD_API_PAGESIZE = "每页显示条数";
    }
}
