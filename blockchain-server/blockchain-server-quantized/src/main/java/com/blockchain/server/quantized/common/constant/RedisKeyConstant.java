package com.blockchain.server.quantized.common.constant;

import java.text.MessageFormat;

/**
 * @author: Liusd
 * @create: 2019-04-26 12:07
 **/
public class RedisKeyConstant {

    public static final String ORDER_LIST = "cct:huobi:order:{0}:{1}-{2}";
    public static final String RECORD_LIST = "cct:huobi:record:{0}-{1}";
    public static final String MARKET_LIST = "cct:huobi:market:{0}-{1}";
    public static final int LIST_SIZE = 140;


    public static String getOrderList(String type, String coinName,String unitName) {
        return MessageFormat.format(ORDER_LIST,type,coinName,unitName);
    }

    public static String getRecordList(String coinName,String unitName) {
        return MessageFormat.format(RECORD_LIST,coinName,unitName);
    }

    public static String getMarketList(String coinName,String unitName) {
        return MessageFormat.format(MARKET_LIST,coinName,unitName);
    }
}
