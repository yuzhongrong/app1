package com.blockchain.server.tron.common.constant;

/**
 * 以太坊钱包常量参数
 *
 * @author YH
 * @date 2019年2月18日15:25:04
 */
public class TronWalletConstants {

    public static class WalletType {
        public final static String WALLET = "WALLET";
        public final static String COIN = "PC";
    }

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
        public static final String TRANSFER_TYPE_INTX = "INTX";        // 内部转账
        //交易类型 转内快速转账
        public static final String FAST = "FAST";
        public static final String PAY = "PAY";   // 支付
        public static final String REFUND = "REFUND";   // 退款
        public static final String RECHARGE = "RECHARGE";   // 奖金发放
    }

    public static class C2cTransfer {
        // 划转
        public static final String TRANSFER_TYPE_OTC = "OTC";   // OTC 划转
        public static final String TRANSFER_TYPE_INVEST = "INVEST";   // 理财 划转
        public static final String IN = "in";   //划转——转入钱包
        public static final String OUT = "out";     // 划转——从钱包转出
    }

    public static class StatusType {
        // 内部转账状态
        public final static int ERROR = 0; //充值失败
        public final static int SUCCESS = 1; //充值成功
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

}
