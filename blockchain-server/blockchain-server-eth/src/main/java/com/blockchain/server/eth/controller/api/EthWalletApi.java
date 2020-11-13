package com.blockchain.server.eth.controller.api;

public class EthWalletApi {
    public static final String MARKET_CONTROLLER_API = "以太坊钱包控制器";

    public static class GetWallet {
        public static final String METHOD_API_NAME = "查询指定的钱包信息";
        public static final String METHOD_API_NOTE = "查询指定的钱包信息";
        public static final String METHOD_API_WALLETTYPE = "钱包类型";
        public static final String METHOD_API_TOKENADDR = "币种地址";
    }

    public static class GetWallets {
        public static final String METHOD_API_NAME = "查询指定应用的所有钱包信息";
        public static final String METHOD_API_NOTE = "查询指定应用的所有钱包信息";
        public static final String METHOD_API_WALLETTYPE = "钱包类型";
    }

    public static class GetPublicKey {
        public static final String METHOD_API_NAME = "获取密码加密的公钥";
        public static final String METHOD_API_NOTE = "获取密码加密的公钥";
        public static final String  METHOD_API_EXPER = "外部系统获取公钥";
    }

    public static class InitWallets {
        public static final String METHOD_API_NAME = "初始化所有的钱包";
        public static final String METHOD_API_NOTE = "初始化所有的钱包";
        public static final String METHOD_API_PASSWORD = "钱包密码";
    }

    public static class CreationWallet {
        public static final String METHOD_API_NAME = "创建指定的钱包信息";
        public static final String METHOD_API_NOTE = "创建指定的钱包信息";
        public static final String METHOD_API_WALLETTYPE = "钱包类型";
        public static final String METHOD_API_TOKENADDR = "币种地址";
        public static final String METHOD_API_PASSWORD = "待验证的钱包密码";
    }


    public static class SaveWalletPass {
        public static final String METHOD_API_NAME = "修改钱包密码";
        public static final String METHOD_API_NOTE = "修改钱包密码";
        public static final String METHOD_API_PASSWORD = "钱包密码";
        public static final String METHOD_API_CODE = "手机验证码";
    }

    public static class ExistsPass {
        public static final String METHOD_API_NAME = "是否设置资金密码";
        public static final String METHOD_API_NOTE = "是否设置资金密码";
    }

    public static class WithdrawDeposit {
        public static final String METHOD_API_NAME = "提现";
        public static final String METHOD_API_NOTE = "提现";
        public static final String METHOD_API_PASSWORD = "钱包密码";
        public static final String METHOD_API_WALLETTYPE = "钱包类型";
        public static final String METHOD_API_TOKENADDR = "币种地址";
        public static final String METHOD_API_TOADDDR = "提现收款地址";
        public static final String METHOD_API_AMOUNT = "提现金额";
        public static final String VERIFY_CODE = "提现验证码";
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
