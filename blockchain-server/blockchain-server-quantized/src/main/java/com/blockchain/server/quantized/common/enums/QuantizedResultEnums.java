package com.blockchain.server.quantized.common.enums;

public enum QuantizedResultEnums {

    TRANSACTION_PAIR_NOT_EXIST(1050, "交易对不存在"),
    CREATE_ORDER_ERROR(1051, "下单出错"),
    CANCEL_ORDER_ERROR(1052, "撤单出错"),
    ORDER_DETAILS_ERROR(1053, "获取订单详情出错"),
    ORDER_MATCHRESULTS_ERROR(1054, "获取订单成交明细出错"),
    ORDER_BALANCE_ERROR(1055, "获取账户余额出错"),
    ORDER_OPENORDERS_ERROR(1056, "查询当前未成交订单出错"),
    ORDER_FILLEDORDERS_ERROR(1057, "查询当前和历史成交订单出错"),
    SERVER_IS_TOO_BUSY(1058, "系统繁忙，请稍后重试"),
    ACCOUNT_NOT_EXIST(1059, "账户不存在"),
    TRANSACTION_PAIR_IS_EXIST(1080, "交易对已存在");



    private int code;
    private String msg;

    QuantizedResultEnums(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
