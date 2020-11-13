package com.blockchain.server.cct.controller.api;

public class TradingDetailApi {
    public static final String DETAIL_API = "成交详情控制器";

    public static class listUserHistory {
        public static final String METHOD_TITLE_NAME = "app端查询用户成交记录";
        public static final String METHOD_TITLE_NOTE = "app端根据条件查询用户成交记录";
        public static final String METHOD_API_COINNAME = "基本货币";
        public static final String METHOD_API_UNITNAME = "二级货币";
        public static final String METHOD_API_BEGINTIME = "开始时间";
        public static final String METHOD_API_LASTTIME = "结束时间";
        public static final String METHOD_API_STATUS = "订单状态";
        public static final String METHOD_API_PAGENUM = "页码";
        public static final String METHOD_API_PAGESIZE = "每页条数";
    }

    public static class listDetailByOrderId {
        public static final String METHOD_TITLE_NAME = "查询用户成交记录";
        public static final String METHOD_TITLE_NOTE = "根据条件查询用户成交记录";
        public static final String METHOD_API_ORDERID = "发布订单id";
        public static final String METHOD_API_PAGENUM = "页码";
        public static final String METHOD_API_PAGESIZE = "每页条数";
    }

    public static class pcListUserHistory {
        public static final String METHOD_TITLE_NAME = "pc端查询用户成交记录";
        public static final String METHOD_TITLE_NOTE = "pc端根据条件查询用户成交记录";
        public static final String METHOD_API_COINNAME = "基本货币";
        public static final String METHOD_API_UNITNAME = "二级货币";
        public static final String METHOD_API_BEGINTIME = "开始时间";
        public static final String METHOD_API_LASTTIME = "结束时间";
        public static final String METHOD_API_STATUS = "订单状态";
        public static final String METHOD_API_PAGENUM = "页码";
        public static final String METHOD_API_PAGESIZE = "每页条数";
    }

    public static class pcListDetailByOrderId {
        public static final String METHOD_TITLE_NAME = "pc端查询用户成交记录";
        public static final String METHOD_TITLE_NOTE = "pc端根据条件查询用户成交记录";
        public static final String METHOD_API_ORDERID = "发布订单id";
        public static final String METHOD_API_PAGENUM = "页码";
        public static final String METHOD_API_PAGESIZE = "每页条数";
    }
}
