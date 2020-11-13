package com.blockchain.server.teth.inner.api;

public class EthWalletTransferApi {
    public static final String MARKET_CONTROLLER_API = "以太坊钱包转账控制器";

    public static class Order {
        public static final String METHOD_API_NAME = "下单与撤单的接口";
        public static final String METHOD_API_NOTE = "下单与撤单的接口";
        public static final String METHOD_API_ORDERDTO = "参数对象";
    }

    public static class Change {
        public static final String METHOD_API_NAME = "余额变动的接口";
        public static final String METHOD_API_NOTE = "余额变动的接口";
        public static final String METHOD_API_CHANGEDTO = "参数对象";
    }
}
