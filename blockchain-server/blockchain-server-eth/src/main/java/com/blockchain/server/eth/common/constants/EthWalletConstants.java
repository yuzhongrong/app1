package com.blockchain.server.eth.common.constants;

/**
 * 以太坊钱包常量参数
 *
 * @author YH
 * @date 2019年2月18日15:25:04
 */
public class EthWalletConstants {
    public static int BASE_PAGESIZE = 10; // 默认页面条数
    public static int WALLERT_LENGTH = 42; // 地址长度


    /**
     * 钱包流水账的转账类型
     */
    public static class TransferType {
        public final static String OUT = "OUT";
        public final static String IN = "IN";
        public final static String CCT = "CCT";
        public final static String GAS = "GAS";
        public final static String TXMIN = "TXMIN"; // 内部转账（自身）
        public final static String TXOIN = "TXOIN"; // 内部转账（他人）
        //交易类型 转内快速转账
        public static final String FAST = "FAST";

        //私募资金
        public final static String PRIVATE_BALANCE = "PRIVATE_BALANCE";
    }

    public static class StatusType {
        // 内部转账状态
        public final static int ERROR = 0; //充值失败
        public final static int SUCCESS = 1; //充值成功
        public final static String GFC_SUCCESS="SUCCESS";//成功
        public final static String GFC_FAIL="FAIL";//失败
        // 充币状态
        public final static int IN_ERROR = 0; // 充值失败
        public final static int IN_SUCCESS = 1; // 充值成功
        public final static int IN_LOAD = 5; // 打包中
        // 提币状态
        public final static int OUT_LOAD1 = 2; // 待初审提币
        public final static int OUT_LOAD2 = 3; // 待复审提币
        public final static int OUT_LOAD3 = 4; // 待出币
        public final static int OUT_LOAD4 = 5; // 已出币（打包中）
        public final static int OUT_ERROR = 6; // 出币失败
        public final static int OUT_SUCCESS = 1; // 出币成功
        public final static int OUT_NOTPASS = 7; // 审核不通过
        // 区块插入状态
        public final static char BLOCK_ERROR = 'N'; //查询失败
        public final static char BLOCK_SUCCESS = 'Y'; //查询成功
        public final static char BLOCK_WAIT = 'W'; //等待
    }
    public static class TokenSymbolConstants {

        //BTC
        public static final String BTC = "BTC";

        //USDT
        public static final String USDT = "USDT";

        //LTC
        public static final String LTC = "LTC";

        //ETH
        public static final String ETH = "ETH";

        //FK
        public static final String FK = "FK";

        //EOS
        public static final String EOS = "EOS";

        //TRX
        public static final String TRX = "TRX";

        public static final String GFC ="GFC";

    }

}
