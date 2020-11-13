package com.blockchain.server.quantized.inner.api;

/**
 * @author: Liusd
 * @create: 2019-04-18 20:11
 **/
public class OrderApi {

    public static final String ORDER_API = "订单控制器";

    /**
     * @Description: 下单
     * @Param:
     * @return:
     * @Author: Liu.sd
     * @Date: 2019/4/19
     */
    public static class Create {
        public static final String METHOD_TITLE_NAME = "下单接口";
        public static final String METHOD_TITLE_NOTE = "下单接口";
        public static final String SYMBOL = "交易对";
        public static final String AMOUNT = "数量";
        public static final String PRICE = "价格";
        public static final String USERID = "业务用户id";
    }

    /**
     * @Description: 撤单
     * @Param:
     * @return:
     * @Author: Liu.sd
     * @Date: 2019/4/19
     */
    public static class Cancel {
        public static final String METHOD_TITLE_NAME = "撤单接口";
        public static final String METHOD_TITLE_NOTE = "撤单接口";
        public static final String SYMBOL = "交易对";
        public static final String ORDERID = "订单号";
    }

    /**
     * @Description: 订单详情
     * @Param:
     * @return:
     * @Author: Liu.sd
     * @Date: 2019/4/19
     */
    public static class Details {
        public static final String METHOD_TITLE_NAME = "订单详情接口";
        public static final String METHOD_TITLE_NOTE = "订单详情接口";
        public static final String SYMBOL = "交易对";
        public static final String ORDERID = "订单号";
    }

    /**
     * @Description: 成交订单明细
     * @Param:
     * @return:
     * @Author: Liu.sd
     * @Date: 2019/4/19
     */
    public static class Matchresults {
        public static final String METHOD_TITLE_NAME = "成交明细接口";
        public static final String METHOD_TITLE_NOTE = "成交明细接口";
        public static final String SYMBOL = "交易对";
        public static final String ORDERID = "订单号";
    }

    public static class OpenOrders {
        public static final String METHOD_TITLE_NAME = "查询当前未成交订单接口";
        public static final String METHOD_TITLE_NOTE = "查询当前未成交订单接口";
        public static final String SYMBOL = "交易对";
    }

    public static class Balance {
        public static final String METHOD_TITLE_NAME = "账户余额接口";
        public static final String METHOD_TITLE_NOTE = "账户余额接口";
    }

    public static class getMarketDTOHistoricalTrade {
        public static final String METHOD_TITLE_NAME = "查询火币历史成交列表";
        public static final String METHOD_TITLE_NOTE = "用于币币查询行情深度数据";
        public static final String METHOD_API_COINNAME = "基本货币";
        public static final String METHOD_API_UNITNAME = "二级货币";
        public static final String METHOD_API_SIZE = "查询记录的数量";
    }

    public static class CheckTradingOnIsOpen {
        public static final String METHOD_TITLE_NAME = "判断币种是否是量化";
        public static final String METHOD_TITLE_NOTE = "判断币种是否是量化";
        public static final String COINNAME = "基本货币";
        public static final String UNITNAME = "二级货币";
    }
}
