package com.blockchain.server.databot.common.enums;

public enum DataBotEnums {
    UNKNOWN_TRADING_TYPE(8901, "未知交易方向！", "Unknown trading direction！", "未知交易方向！"),
    UNKNOWN_STATUS(8902, "未知状态！", "Unknown state！", "未知狀態"),
    ;
    private int code;
    private String cnmsg;
    private String enMsg;
    private String hkmsg;

    DataBotEnums(int code, String cnmsg, String enMsg, String hkmsg) {
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
