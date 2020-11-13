package com.blockchain.server.otc.common.constant;

/***
 * 用户操作日志表常量
 */
public class UserHandleConstants {

    /***
     * 用户操作类型
     */
    public static final String PUBLISH = "PUBLISH"; //发布广告
    public static final String DEAL = "DEAL"; //买入或出售
    public static final String PAY = "PAY"; //确认付款
    public static final String RECEIPT = "RECEIPT"; //确认收款
    public static final String CANCEL = "CANCEL"; //撤销
    public static final String APPEAL = "APPEAL"; //申诉
    public static final String PENDING = "PENDING"; //下架广告
    public static final String DEFAULT = "DEFAULT"; //上架广告

    /***
     * 用户操作的记录类型
     */
    public static final String AD = "AD"; //广告类型
    public static final String ORDER = "ORDER"; //订单类型
}
