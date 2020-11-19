package com.blockchain.server.cmc.common.constants;

/**
 * @author hugq
 * @date 2019/2/16 0016 16:31
 */
public class TransferConstans {

    //交易类型 充值
    public static final String TYPE_IN = "IN";
    //交易类型 提现
    public static final String TYPE_OUT = "OUT";
    //交易类型 币币交易
    public static final String TYPE_CCT = "CCT";
    //交易类型 手续费
    public static final String TYPE_GAS = "GAS";
    //交易类型 转内快速转账
    public static final String TYPE_FAST = "FAST";


    //交易状态 失败
    public static final int STATUS_FILE = 0;
    //交易状态 成功
    public static final int STATUS_SUCCESS = 1;
    //交易状态 待初审
    public static final int STATUS_FIRST_TRIAL = 2;
    //交易状态 待复审
    public static final int STATUS_SECOND_TRIAL = 3;
    //交易状态 待出币
    public static final int STATUS_STAY_OUT = 4;
    //交易状态 已出币
    public static final int STATUS_ALREADY_OUT = 5;
    //交易状态 出币失败
    public static final int STATUS_OUT_FILE = 6;
    //交易状态 审核不通过
    public static final int STATUS_TRIAL_FILE = 7;

}
