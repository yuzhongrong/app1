package com.blockchain.server.cmc.common.constants;

/**
 * @author hugq
 * @date 2019/2/16 0016 16:31
 */
public class AddressConstans {

    //redis 存储地址列表的key值，用于解析用户充值区块信息
    public static final String ADDRESS_KEY = "ltc:adderss:addressSet";
    //redis 存储地址列表的时间，天数
    public static final int ADDRESS_TIME = 30;

}
