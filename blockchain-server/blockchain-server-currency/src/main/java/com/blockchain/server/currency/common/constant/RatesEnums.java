package com.blockchain.server.currency.common.constant;

/**
 * \* <p>Desciption:汇率枚举</p>
 * \* CreateTime: 2019/3/21 20:00
 * \* User: XianChaoWei
 * \* Version: V1.0
 * \
 */
public enum RatesEnums {
    CNY("CNY"),
    USD("USD"),
    HKD("HKD"),
    EUR("EUR")
    ;

    private String value;

    RatesEnums(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
