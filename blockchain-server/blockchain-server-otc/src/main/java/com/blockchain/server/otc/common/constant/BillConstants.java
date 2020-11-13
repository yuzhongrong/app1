package com.blockchain.server.otc.common.constant;

/***
 * 资金对账表常量
 */
public class BillConstants {

    /***
     * 资金变动类型
     */
    public static final String PUBLISH = "PUBLISH"; //发布广告
    public static final String DEAL = "DEAL"; //买入或卖出
    public static final String MARK = "MARK"; //成交
    public static final String CANCEL = "CANCEL"; //撤销
    public static final String AUTO_CANCEL = "AUTO_CANCEL"; //自动撤销
    public static final String MARKET_USER = "MARKET_USER";//成为市商
    public static final String CANCEL_MARKET_USER = "CANCEL_MARKET_USER";//取消市商

}
