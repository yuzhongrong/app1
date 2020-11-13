package com.blockchain.server.tron.common.constant;

/**
 * @author Harvey Luo
 * @date 2019/6/10 14:20
 */
public class TronConstant {

    public static final String TRON_TOKEN_ID = "TRX";
    public static final String TRON_TOKEN_TYPE = "trx";
    public static final String DELIMIT="_";

    public static class TronToken {
        public static final Integer TRON_TOKEN_DECIMALS = 6;
        public static final String TRON_TOKEN_SYMBOL_ACE = "ACE";
    }

    public static class TronGasConfig {
        public static final String TRON_GAS_PRIFIX = "trx_gas_config_";
        public static final String TRON_GAS_STATUS_USABLE = "1";
        public static final String TRON_GAS_STATUS_FORBIDDEN = "0";
    }

    public static class BlockNumber {
        // 区块状态处理失败
        public static final Character TRON_BLOCK_NUMBER_ERROR = 'N';
        // 区块状态处理成功
        public static final Character TRON_BLOCK_NUMBER_SUCCESS = 'Y';
        // 区块状态等待处理
        public static final Character TRON_BLOCK_NUMBER_WAIT = 'W';
    }

    public static class RedisKey {
        //  储存所有钱包地址对象的key值
        public static final String TRON_WALLET_ADDRS = "tron:wallet:addrs";
        public static final String TRON_BITMAP_KEY = "tron:blockchain:bitmap";
        // 现在区块高度
        public static final String TRON_BLOCKCHAIN_CURRENT = "tron:blockchain:current";
        public static final String TRON_BLOCKCHAIN_OPT_HASH = "tron:blockchain:opt:hash";
        public static final String TRON_WALLET_IN_REDIS = "tron:wallet:in";
        public static final String TRON_BLOCKCHAIN_RTC20_TOKEN = "tron:blockchain:trc20";
        public static final String TRON_WALLET_KEY_INIT_KEY = "tron:blockchain:wallet:init_key";
        public static final String TRON_WALLET_KEY_INIT_KEY_TIME = "tron:blockchain:wallet:init_key_time";
    }

    public static class WalletAccountStatus {
        // 充值钱包账户可用
        public static final Integer TRON_WALLET_USABLE_USABLE = 1;
        // 充值钱包账户禁用
        public static final Integer TRON_WALLET_IN_FORBIDDEN = 0;
    }

    public static class TransferType {
        /**
         * 交易状态充值合同
         */
        public static final String TRANSFER_CONTRACT = "TransferContract";
        /**
         * 交易状态充值资产合同
         */
        public static final String TRANSFER_ASSET_CONTRACT = "TransferAssetContract";
        /**
         * 提现
         */
        public static final String TRANSFER_OUT = "OUT";
        /**
         * 充值
         */
        public static final String TRANSFER_IN = "IN";
        /**
         * 币币交易
         */
        public static final String TRANSFER_CCT = "CCT";
        /**
         * 手续费
         */
        public static final String TRANSFER_GAS = "GAS";

        //交易类型 转内快速转账
        public static final String TYPE_FAST = "FAST";
    }

    public static class TransferStatus {
        // 失败
        public static final Integer DEFEATED = 0;
        // 成功
        public static final Integer SUCCESS = 1;
        // 待初审提币
        public static final Integer FIRST_TRIAL = 2;
        // 待复审提币
        public static final Integer RECHECK = 3;
        // 待出币
        public static final Integer DAI_CHU_BI = 4;
        // 已出币
        public static final Integer YI_CHU_BI = 5;
        // 出币失败
        public static final Integer CHU_BI_DEFEATED = 6;
        // 审核不通过
        public static final Integer REJECTED = 7;
    }

    public static class RPCConstant {
        public static final Integer OFFSET = 40;
        public static final Integer ADD_OFFSET = 40;
        public static final Integer OFFSET_MAX = 200;

        public static final String RPC_ONLY_TO = "?only_to=true";
        public static final String RPC_LIMIT = "&limit=";
        public static final String RPC_EVENT_NAME = "?event_name=Transfer";
        public static final String RPC_MIN_BLOCK_TIMESTAMP = "&min_block_timestamp=";
        public static final String RPC_MAX_BLOCK_TIMESTAMP = "&max_block_timestamp=";
        public static final String RPC_ORDER_BY_DESC = "&order_by=block_timestamp,desc";
    }

    public static class ResultType {
        public static final String RESULT_SUCCESS = "SUCCESS";
    }

    public static class AddressConstant {
        public static final String HEX_FORMAT = "Hex string format";
        public static final String BASE_58_FORMAT = "Base58check format";
        public static final String BASE_64_FORMAT = "Base64 format";
        public static final String FORMAT_EOORO = "Length error";
    }

    public static class InformConstant {
        public static final int PAY_TIME = 5; // 通知次数
        public static final int STATUS_PEND = 0;     // 待处理
        public static final int STATUS_SUCCEED = 1;   //  已处理
        public static final String TYPE_PAY = "PAY";   // 支付
    }

    public static class InitWalletKey {
        public static final Long WALLET_COUNT = 100L;
        public static final Long MIN_WALLET_COUNT = 70L;
    }

}
