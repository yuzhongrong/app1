package com.blockchain.server.btc.inner.api;

public class BtcWalletInnerApi {
    public static final String MARKET_CONTROLLER_API = "比特币钱包控制器，内部调用";

    public static class CreateWallet {
        public static final String METHOD_API_NAME = "创建钱包";
        public static final String METHOD_API_NOTE = "创建钱包";
        public static final String USEROPENID = "用户id";
    }

    public static class GetBalanceByIdAndTypes {
        public static final String METHOD_API_NAME = "获取用户钱包余额";
        public static final String METHOD_API_NOTE = "获取用户钱包余额";
        public static final String USEROPENID = "用户id";
        public static final String WALLET_TYPE = "钱包账户类型";
    }

}
