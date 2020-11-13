package com.blockchain.server.otc.common.constant;

/***
 * 广告表常量
 */
public class AdConstants {

    /***
     * 广告状态
     */
    public static final String DEFAULT = "DEFAULT"; //挂单中
    public static final String UNDERWAY = "UNDERWAY"; //交易中
    public static final String PENDING = "PENDING"; //待处理：当订单需要下架、修改时变成待处理
    public static final String FINISH = "FINISH"; //已完成
    public static final String CANCEL = "CANCEL"; //已撤销
}
