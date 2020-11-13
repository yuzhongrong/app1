package com.blockchain.common.base.constant;

/**
 * \* <p>Desciption:汇率枚举</p>
 * \* CreateTime: 2019/3/21 20:00
 * \* User: XianChaoWei
 * \* Version: V1.0
 * \
 */
public enum TokenTypeEnums {
    APP("APP"),
    PC("PC")
    ;

    private String value;

    TokenTypeEnums(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
