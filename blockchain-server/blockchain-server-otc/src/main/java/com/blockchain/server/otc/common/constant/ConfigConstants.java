package com.blockchain.server.otc.common.constant;

/***
 * 配置表常量
 */
public class ConfigConstants {

    /***
     * 配置信息key
     */
    public static final String AD_SERVICE_CHARGE = "ad_service_charge"; //广告方是否收取手续费
    public static final String ORDER_SERVICE_CHARGE = "order_service_charge"; //订单方是否收取手续费
    public static final String AUTO_CANCEL = "auto_cancel"; //自动撤单是否开启
    public static final String AUTO_CANCEL_INTERVAL = "auto_cancel_interval"; //自动撤单时间间隔
    public static final String SELL_SERVICE_CHARGE = "sell_service_charge"; //卖家是否收取手续费
    public static final String MARKET_SELL_COUNT = "market_sell_ad_count"; //市商可发布多少卖单
    public static final String MARKET_BUY_COUNT = "market_buy_ad_count"; //市商可发布多少买单
    public static final String MARKET_FREEZE_COIN = "market_freeze_coin"; //市商押金代币
    public static final String MARKET_FREEZE_AMOUNT = "market_freeze_amount"; //市商押金数量
}
