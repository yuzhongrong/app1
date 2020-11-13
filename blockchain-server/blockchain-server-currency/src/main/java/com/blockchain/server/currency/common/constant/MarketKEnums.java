package com.blockchain.server.currency.common.constant;

/**
 * \* <p>Desciption:</p>
 * \* CreateTime: 2019/2/25 10:50
 * \* User: XianChaoWei
 * \* Version: V1.0
 * \
 */
public enum MarketKEnums {
    MINUTE("MINUTE", 60000),
    HOUR("HOUR", 3600000),
    DAY("DAY", 86400000),
    WEEK("WEEK", 604800000),
    MONTH("MONTH", 0);

    private String value;
    private int second;

    MarketKEnums(String value, int second) {
        this.value = value;
        this.second = second;
    }

    public String getValue() {
        return value;
    }

    public int getSecond() {
        return second;
    }
}
