package com.blockchain.server.currency.common.constant;

/**
 * \* <p>Desciption:</p>
 * \* CreateTime: 2019/2/25 10:50
 * \* User: XianChaoWei
 * \* Version: V1.0
 * \
 */
public enum MarketKTypeEnums {
    ONEMINUTE("MINUTE", 1),
    FIVEMINUTE("MINUTE", 5),
    FIFMINUTE("MINUTE", 15),
    THRMINUTE("MINUTE", 30),
    ONEHOUR("HOUR", 1),
    fourHOUR("HOUR", 4),
    TWHOUR("HOUR", 12),
    ONEDAY("DAY", 1),
    ONEWEEK("WEEK", 1);

    private String timeType;
    private int timeNumber;

    MarketKTypeEnums(String timeType, int timeNumber) {
        this.timeType = timeType;
        this.timeNumber = timeNumber;
    }

    public String getTimeType() {
        return timeType;
    }

    public int getTimeNumber() {
        return timeNumber;
    }

}
