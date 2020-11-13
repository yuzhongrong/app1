package com.blockchain.server.currency.common.constant;

/**
 * \* <p>Desciption:主币</p>
 * \* CreateTime: 2019/3/21 20:00
 * \* User: XianChaoWei
 * \* Version: V1.0
 * \
 */
public enum BaseCoinEnums {
    BTC("BTC"),
    ETH("ETH"),
    EOS("EOS"),
    USDT("USDT")
    ;

    private String value;

    BaseCoinEnums(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
