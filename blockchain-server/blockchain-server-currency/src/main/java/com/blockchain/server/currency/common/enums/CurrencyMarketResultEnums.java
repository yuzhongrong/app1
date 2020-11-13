package com.blockchain.server.currency.common.enums;

public enum CurrencyMarketResultEnums {
    CURRENCY_PAIR_NULL(4001,"币对不能为空","Currency pair can not be null","幣懟不能為空"),
    CURRENCY_PAIR_ERROR(4002,"币对不存在","Currency pair is not find","幣懟不存在"),
    CURRENCY_PAIR_UNUSABLE(4002,"币对不可用","Currency pair is usabled","幣懟不可用");
    private int code;
    private String cnmsg;
    private String enMsg;
    private String hkmsg;

    CurrencyMarketResultEnums(int code, String cnmsg, String enMsg, String hkmsg) {
        this.code = code;
        this.cnmsg = cnmsg;
        this.enMsg = enMsg;
        this.hkmsg = hkmsg;
    }

    public int getCode() {
        return code;
    }

    public String getCnmsg() {
        return cnmsg;
    }

    public String getEnMsg() {
        return enMsg;
    }

    public String getHkmsg() {
        return hkmsg;
    }
}
