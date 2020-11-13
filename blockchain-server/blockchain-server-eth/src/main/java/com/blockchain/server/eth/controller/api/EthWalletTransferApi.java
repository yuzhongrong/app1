package com.blockchain.server.eth.controller.api;

public class EthWalletTransferApi {
    public static final String MARKET_CONTROLLER_API = "以太坊钱包控制器";
    public static final String PAGENUM = "当前页数";
    public static final String PAGESIZE = "页数展示条数";

    public static class GetTransfer {
        public static final String METHOD_API_WALLETTYPE = "钱包类型";
        public static final String METHOD_API_TOKENADDR = "币种地址";
    }

    public static class pcGetTransfer {
        public static final String METHOD_API_WALLETTYPE = "钱包类型";
        public static final String METHOD_API_TOKENADDR = "币种地址";
    }
}
