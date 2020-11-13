package com.blockchain.server.eth.inner.api;

public class EthWalletApi {
    public static final String MARKET_CONTROLLER_API = "以太坊钱包控制器";

    public static class GetPublicKey {
        public static final String METHOD_API_NAME = "获取密码加密的公钥";
        public static final String METHOD_API_NOTE = "获取密码加密的公钥";
        public static final String METHOD_API_USERID = "用户标识";
    }

    public static class IsPassword {
        public static final String METHOD_API_NAME = "验证钱包密码";
        public static final String METHOD_API_NOTE = "验证钱包密码";
        public static final String METHOD_API_PASSWORD = "钱包密码";
        public static final String METHOD_API_EXPER = "外部交易检验密码";
    }

    public static class InitWallets {
        public static final String METHOD_API_NAME = "初始化所有的钱包";
        public static final String METHOD_API_NOTE = "初始化所有的钱包";
        public static final String METHOD_API_USERID = "用户标识";
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

    public static class GetBalanceByIdAndTypes {
        public static final String METHOD_API_NAME = "获取用户钱包余额";
        public static final String METHOD_API_NOTE = "获取用户钱包余额";
        public static final String USEROPENID = "用户id";
        public static final String WALLET_TYPE = "钱包账户类型";
    }

    public static class GetAllBalanceByToken {
        public static final String METHOD_API_NAME = "获取用户某个币种所有金额";
        public static final String METHOD_API_NOTE = "获取用户某个币种所有金额";
        public static final String USEROPENID = "用户id";
        public static final String COINNAME = "币种名称";
    }

}
