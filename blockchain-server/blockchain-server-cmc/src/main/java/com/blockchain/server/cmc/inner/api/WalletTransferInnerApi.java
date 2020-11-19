package com.blockchain.server.cmc.inner.api;

public class WalletTransferInnerApi {
    public static final String MARKET_CONTROLLER_API = "钱包转账控制器，内部调用";

    public static class Order {
        public static final String METHOD_API_NAME = "币币交易，冻结、解冻接口";
        public static final String METHOD_API_NOTE = "币币交易，冻结、解冻接口";
        public static final String WALLETORDERDTO = "交易参数";
    }

    public static class Change {
        public static final String METHOD_API_NAME = "钱包余额变动接口";
        public static final String METHOD_API_NOTE = "钱包余额变动接口";
        public static final String WALLETCHANGEDTO = "变动参数";
    }

}
