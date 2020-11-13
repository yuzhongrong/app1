package com.blockchain.server.ltc.controller.api;

public class WalletApi {
    public static final String MARKET_CONTROLLER_API = "莱特币钱包控制器";

    public static final String PAGENUM = "当前页数";
    public static final String PAGESIZE = "页数展示条数";

    public static class GetWallet {
        public static final String METHOD_API_NAME = "获取用户钱包";
        public static final String METHOD_API_NOTE = "获取用户钱包";
        public static final String TOKENID = "币种id";
        public static final String WALLETTYPE = "应用类型：CCT-币币账户，C2C-法币账户，POOL-矿池账户，FORCE-原力值账户";
    }

    public static class GetWallets {
        public static final String METHOD_API_NAME = "获取用户钱包所有币种信息";
        public static final String METHOD_API_NOTE = "获取用户钱包所有币种信息";
        public static final String WALLETTYPE = "应用类型：CCT-币币账户，C2C-法币账户，POOL-矿池账户，FORCE-原力值账户";
    }
    public static class Transfer {
        public static final String METHOD_API_NAME = "钱包划转";
        public static final String METHOD_API_NOTE = "钱包划转";
        public static final String METHOD_API_FROMTYPE = "支付方钱包类型";
        public static final String METHOD_API_TOTYPE = "收款钱包类型";
        public static final String METHOD_API_COINNAME = "划转币种的名称";
        public static final String METHOD_API_AMOUNT = "金额";
    }
}
