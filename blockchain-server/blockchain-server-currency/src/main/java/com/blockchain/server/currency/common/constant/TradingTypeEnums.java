package com.blockchain.server.currency.common.constant;

/**
 * \* <p>Desciption:汇率枚举</p>
 * \* CreateTime: 2019/3/21 20:00
 * \* User: XianChaoWei
 * \* Version: V1.0
 * \
 */
public enum TradingTypeEnums {
    BUY("BUY"),
    SELL("SELL"),
    ;

    private String value;

    TradingTypeEnums(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
