package com.blockchain.server.currency.common.constant;

/**
 * \* <p>Desciption:</p>
 * \* CreateTime: 2019/2/25 10:50
 * \* User: XianChaoWei
 * \* Version: V1.0
 * \
 */
public enum BaseCurrencyEnums {
    BTC("BTC"),
    ETH("ETH"),
    EOS("EOS"),
    ;

    private String value;

    BaseCurrencyEnums(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
