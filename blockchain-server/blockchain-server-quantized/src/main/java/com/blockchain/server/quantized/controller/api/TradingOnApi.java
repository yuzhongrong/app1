package com.blockchain.server.quantized.controller.api;

/**
 * @author: Liusd
 * @create: 2019-03-25 13:51
 **/
public class TradingOnApi {

    public static final String CONTROLLER_API = "交易对控制器";

    public static class Add {
        public static final String METHOD_TITLE_NAME = "rpc调用";
        public static final String METHOD_TITLE_NOTE = "交易对发生变化重新订阅火币";
        public static final String METHOD_API_COINNAME = "基本货币";
        public static final String METHOD_API_UNITNAME = "二级货币";
    }
}
