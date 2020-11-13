package com.blockchain.server.eos.common.constant;

/**
 * @author Harvey
 * @date 2019/2/19 15:17
 * @user WIN10
 */
public class EosConstant {

    public static final String EOS_TOKEN_NAME = "eosio.token";
    public static final String EOS_TOKEN_TYPE = "eos";

    public static class EosGasConfig {
        public static final String EOS_GAS_PRIFIX = "eos_gas_config_";
        public static final String EOS_GAS_STATUS_USABLE = "1";
        public static final String EOS_GAS_STATUS_FORBIDDEN = "0";
    }

    public static class RedisKey {
        public static final String EOS_BITMAP_KEY = "eos:blockchain:bitmap";
        public static final String EOS_BLOCKCHAIN_BEGIN = "eos:blockchain:begin";
        public static final String EOS_BLOCKCHAIN_CURRENT = "eos:blockchain:current";
        public static final String EOS_BLOCKCHAIN_OPT_HASH = "eos:blockchain:opt:hash";

    }

    public static class BlockNumber {
        // 区块状态处理失败
        public static final Character EOS_BLOCK_NUMBER_ERROR = 'N';
        // 区块状态处理成功
        public static final Character EOS_BLOCK_NUMBER_SUCCESS = 'Y';
        // 区块状态等待处理
        public static final Character EOS_BLOCK_NUMBER_WAIT = 'W';
    }

    public static class WalletInStatus {
        // 充值钱包账户可用
        public static final Integer EOS_WALLET_IN_USABLE = 1;
        // 充值钱包账户禁用
        public static final Integer EOS_WALLET_IN_FORBIDDEN = 0;
    }

    public static class TransferType {
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
        public static final Integer OFFSET = -50;
        public static final Integer ADD_OFFSET = -50;
        public static final Integer OFFSET_MAX = -500;
    }
}