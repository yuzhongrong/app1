package com.blockchain.server.otc.controller.api;

public class OrderApi {
    public static final String ORDER_API = "订单控制器";
    public static final String METHOD_API_PAGE_NUM = "页码";
    public static final String METHOD_API_PAGE_SIZE = "页数";
    public static final String METHOD_API_COIN_NAME = "基本货币";
    public static final String METHOD_API_UNIT_NAME = "二级货币";
    public static final String METHOD_API_BEGIN_TIME = "开始时间";
    public static final String METHOD_API_END_TIME = "结束时间";

    public static class buyOrder {
        public static final String METHOD_TITLE_NAME = "买入";
        public static final String METHOD_TITLE_NOTE = "买入";
        public static final String METHOD_TITLE_AD_ID = "广告id";
        public static final String METHOD_TITLE_AMOUNT = "下单数量";
        public static final String METHOD_TITLE_PRICE = "下单单价";
        public static final String METHOD_TITLE_TURNOVER = "下单交易额";
    }

    public static class sellOrder {
        public static final String METHOD_TITLE_NAME = "卖出";
        public static final String METHOD_TITLE_NOTE = "卖出";
        public static final String METHOD_TITLE_AD_ID = "广告id";
        public static final String METHOD_TITLE_AMOUNT = "下单数量";
        public static final String METHOD_TITLE_PRICE = "下单单价";
        public static final String METHOD_TITLE_PASS = "支付密码";
        public static final String METHOD_TITLE_TURNOVER = "下单交易额";
    }

    public static class cancelBuyOrder {
        public static final String METHOD_TITLE_NAME = "撤销买入订单";
        public static final String METHOD_TITLE_NOTE = "撤销买入订单";
        public static final String METHOD_TITLE_ORDER_ID = "订单id";
    }

    public static class pay {
        public static final String METHOD_TITLE_NAME = "确认支付";
        public static final String METHOD_TITLE_NOTE = "确认支付";
        public static final String METHOD_TITLE_ORDER_ID = "订单id";
        public static final String METHOD_TITLE_PAY_TYPE = "支付类型";
    }

    public static class receipt {
        public static final String METHOD_TITLE_NAME = "确认收款";
        public static final String METHOD_TITLE_NOTE = "确认收款";
        public static final String METHOD_TITLE_ORDER_ID = "订单id";
        public static final String METHOD_TITLE_PASS = "支付密码";
    }

    public static class listUserOrder {
        public static final String METHOD_TITLE_NAME = "查询用户订单";
        public static final String METHOD_TITLE_NOTE = "查询用户订单";
        public static final String METHOD_TITLE_COIN_NAME = "基本货币";
        public static final String METHOD_TITLE_UNIT_NAME = "二级货币";
        public static final String METHOD_TITLE_ORDER_TYPE = "订单类型";
        public static final String METHOD_TITLE_ORDER_STATUS = "订单状态";
        public static final String METHOD_TITLE_PAY_TYPE = "支付类型";
        public static final String METHOD_TITLE_PAGE_NUM = "页码";
        public static final String METHOD_TITLE_PAGE_SIZE = "每页条数";
    }

    public static class selectByUserIdAndId {
        public static final String METHOD_TITLE_NAME = "根据用户id和订单id查询订单";
        public static final String METHOD_TITLE_NOTE = "根据用户id和订单id查询订单";
        public static final String METHOD_TITLE_ORDER_ID = "订单id";
    }

    public static class pcListUserOrder {
        public static final String METHOD_TITLE_NAME = "查询用户订单";
        public static final String METHOD_TITLE_NOTE = "查询用户订单";
        public static final String METHOD_TITLE_COIN_NAME = "基本货币";
        public static final String METHOD_TITLE_UNIT_NAME = "二级货币";
        public static final String METHOD_TITLE_ORDER_TYPE = "订单类型";
        public static final String METHOD_TITLE_ORDER_STATUS = "订单状态";
        public static final String METHOD_TITLE_PAY_TYPE = "支付类型";
        public static final String METHOD_TITLE_PAGE_NUM = "页码";
        public static final String METHOD_TITLE_PAGE_SIZE = "每页条数";
    }

}
