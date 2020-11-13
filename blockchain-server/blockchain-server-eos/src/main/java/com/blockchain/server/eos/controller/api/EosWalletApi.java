package com.blockchain.server.eos.controller.api;

/**
 * @author Harvey
 * @date 2019/2/19 18:10
 * @user WIN10
 */
public class EosWalletApi {
    public static final String EOS_WALLET_API = "钱包控制器";

    public static class WithdrawDeposit {
        public static final String MATHOD_API_NAME = "提现";
        public static final String MATHOD_API_NOTE = "提现";

        public static final String MATHOD_API_PASSWORD = "提现密码";
        public static final String MATHOD_API_ACCOUNT = "提现账号";
        public static final String MATHOD_API_PRIVATE_KEY = "提现账户私钥";
        public static final String MATHOD_API_AMOUNT = "提现金额";
        public static final String MATHOD_API_TOKEN_NAME = "代币地址";
        public static final String MATHOD_API_WALLET_TYPE = "钱包标识";
        public static final String MATHOD_API_REMARK = "备注";
        public static final String VERIFY_CODE = "提现验证码";
    }

    public static class SelectWalletByWalletType {
        public static final String MATHOD_API_NAME = "查询钱包";
        public static final String MATHOD_API_NOTE = "根据钱包标识查询钱包";
        public static final String MATHOD_API_WALLET_TYPE = "钱包标识";
    }

    public static class SelectWalletByTokenName {
        public static final String MATHOD_API_NAME = "查询钱包";
        public static final String MATHOD_API_NOTE = "根据钱包标识和代币名称查询钱包";
        public static final String MATHOD_API_WALLET_TYPE = "查询标识";
        public static final String MATHOD_API_TOKEN_NAME = "币种名称";
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
