package com.blockchain.server.cmc.controller.api;

public class WalletTransferApi {
    public static final String MARKET_CONTROLLER_API = "莱特币交易控制器";

    public static final String PAGENUM = "当前页数";
    public static final String PAGESIZE = "页数展示条数";

    public static class GetTransfer {
        public static final String METHOD_API_NAME = "app端查询所有交易记录";
        public static final String METHOD_API_NOTE = "app端查询所有交易记录";
        public static final String TOKENID = "币种id";
        public static final String WALLET_TYPE = "应用类型";
    }

    public static class pcGetTransfer {
        public static final String METHOD_API_NAME = "pc端查询所有交易记录";
        public static final String METHOD_API_NOTE = "pc端查询所有交易记录";
        public static final String TOKENID = "币种id";
        public static final String WALLET_TYPE = "应用类型";
    }

    public static class Withdraw {
        public static final String METHOD_API_NAME = "提现";
        public static final String METHOD_API_NOTE = "提现";
        public static final String TOKENID = "币种id";
        public static final String WALLET_TYPE = "应用类型";
        public static final String TOADDR = "接收地址";
        public static final String AMOUNT = "提现数量";
        public static final String PASSWORD = "加密密码";
        public static final String VERIFY_CODE = "提现验证码";
    }

}
