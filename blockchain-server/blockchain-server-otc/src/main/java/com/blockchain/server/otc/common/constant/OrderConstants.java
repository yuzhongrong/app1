package com.blockchain.server.otc.common.constant;

/***
 * 订单表常量
 */
public class OrderConstants {

    /***
     * 订单状态和用户操作状态
     */
    public static final String NEW = "NEW"; //订单状态和用户操作状态：新建
    public static final String UNDERWAY = "UNDERWAY"; //订单状态：进行中
    public static final String FINISH = "FINISH"; //订单状态：已完成
    public static final String CANCEL = "CANCEL"; //订单状态和用户操作状态：已撤销
    public static final String APPEAL = "APPEAL"; //订单状态和用户操作状态：申诉
    public static final String CONFIRM = "CONFIRM"; //用户操作状态：已确认
}
