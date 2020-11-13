package com.blockchain.server.currency.common.constant;

/**
 * \* <p>Desciption:</p>
 * \* CreateTime: 2019/2/22 9:38
 * \* User: XianChaoWei
 * \* Version: V1.0
 * \
 */
public enum DatatablesEnums {
    USABLE_STATUS(1,"可用"),
    UNUSABLE_STATUS(0,"不可用")
    ;

    private Object value;
    private String remark;

    DatatablesEnums(Object value, String remark) {
        this.value = value;
        this.remark = remark;
    }

    public Object getValue() {
        return value;
    }

    public String getRemark() {
        return remark;
    }
}
