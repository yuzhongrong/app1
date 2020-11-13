package com.blockchain.server.cct.controller.api;

public class PublishOrderApi {
    public static final String ORDER_API = "发布订单控制器";

    public static class handleLimitBuyOrder {
        public static final String METHOD_TITLE_NAME = "发布限价买单";
        public static final String METHOD_TITLE_NOTE = "发布限价买单";
        public static final String METHOD_API_PARAM_DTO = "发布参数";
    }

    public static class handleLimitSellOrder {
        public static final String METHOD_TITLE_NAME = "发布限价卖单";
        public static final String METHOD_TITLE_NOTE = "发布限价卖单";
        public static final String METHOD_API_PARAM_DTO = "发布参数";
    }

    public static class handleMarketBuyOrder {
        public static final String METHOD_TITLE_NAME = "发布市价买单";
        public static final String METHOD_TITLE_NOTE = "发布市价买单";
        public static final String METHOD_API_PARAM_DTO = "发布参数";
    }

    public static class handleMarketSellOrder {
        public static final String METHOD_TITLE_NAME = "发布市价卖单";
        public static final String METHOD_TITLE_NOTE = "发布市价卖单";
        public static final String METHOD_API_PARAM_DTO = "发布参数";
    }

    public static class cancel {
        public static final String METHOD_TITLE_NAME = "撤销订单";
        public static final String METHOD_TITLE_NOTE = "撤销订单";
        public static final String METHOD_API_ORDER_ID = "订单id";
    }

    public static class listUserOrder {
        public static final String METHOD_TITLE_NAME = "app端查询用户订单";
        public static final String METHOD_TITLE_NOTE = "app端查询用户订单";
        public static final String METHOD_API_COINNAME = "基本货币";
        public static final String METHOD_API_UNITNAME = "二级货币";
        public static final String METHOD_API_TYPE = "订单类型";
        public static final String METHOD_API_PAGENUM = "页码";
        public static final String METHOD_API_PAGESIZE = "分页条数";
    }

    public static class pcListUserOrder {
        public static final String METHOD_TITLE_NAME = "pc端查询用户订单";
        public static final String METHOD_TITLE_NOTE = "pc端查询用户订单";
        public static final String METHOD_API_COINNAME = "基本货币";
        public static final String METHOD_API_UNITNAME = "二级货币";
        public static final String METHOD_API_TYPE = "订单类型";
        public static final String METHOD_API_PAGENUM = "页码";
        public static final String METHOD_API_PAGESIZE = "分页条数";
    }

    public static class listBuyOrder {
        public static final String METHOD_TITLE_NAME = "查询盘口买单";
        public static final String METHOD_TITLE_NOTE = "查询盘口买单";
        public static final String METHOD_API_COINNAME = "基本货币";
        public static final String METHOD_API_UNITNAME = "二级货币";
        public static final String METHOD_API_PAGENUM = "页码";
        public static final String METHOD_API_PAGESIZE = "分页条数";
    }

    public static class listSellOrder {
        public static final String METHOD_TITLE_NAME = "查询盘口卖单";
        public static final String METHOD_TITLE_NOTE = "查询盘口卖单";
        public static final String METHOD_API_COINNAME = "基本货币";
        public static final String METHOD_API_UNITNAME = "二级货币";
        public static final String METHOD_API_PAGENUM = "页码";
        public static final String METHOD_API_PAGESIZE = "分页条数";
    }

    public static class listOrderByCoinAndUnit {
        public static final String METHOD_TITLE_NAME = "查询成交历史";
        public static final String METHOD_TITLE_NOTE = "查询成交历史-用于行情深度图";
        public static final String METHOD_API_COINNAME = "基本货币";
        public static final String METHOD_API_UNITNAME = "二级货币";
    }
}
