package com.blockchain.server.cct.common.enums;

import lombok.Getter;

public enum CctEnums {
    ORDER_STATUS_NO_MATCH(5000, "订单正在撮合中...", "Orders are being matched...", "訂單正在撮合中..."),
    ORDER_USERID_NULL(5001, "用户id获取失败！", "User id retrieval failed！", "用戶id獲取失敗！"),
    ORDER_USERMOBILE_NULL(5002, "用户手机获取失败！", "User phone access failed！", "用戶手機獲取失敗！"),
    ORDER_PASS_NULL(5003, "密码为空！", "Password is empty！", "密碼為空！"),
    ORDER_COINNAME_NULL(5004, "交易币对为空！", "The trading pair is short！", "交易幣對為空！"),
    ORDER_UNITNAME_NULL(5005, "交易币对为空！", "The trading pair is short！", "交易幣對為空！"),
    ORDER_PRICE_NULL(5006, "委托单价为空！", "The entrustment unit price is empty！", "委托單價為空！"),
    ORDER_NUM_NULL(5007, "委托数量为空！", "The number of delegates is empty！", "委托數量為空！"),
    ORDER_TURNOVER_NULL(5008, "交易额为空！", "The trading volume is empty！", "交易額為空！"),
    ORDER_TURNOVER_ERROR(5009, "交易额不合法！", "Illegal trading volume！", "交易額不合法！"),
    ORDER_PRICE_ERROR(5010, "委托单价不合法！", "The entrustment unit price is illegal！", "委托單價不合法！"),
    ORDER_NUM_ERROR(5011, "委托数量不合法！", "The number of delegates is not legal！", "委托數量不合法！"),
    ORDER_ID_NULL(5012, "订单获取失败！", "Order acquisition failure！", "訂單獲取失敗！"),
    ORDER_NULL(5013, "订单不存在！", "Order does not exist！", "訂單不存在！"),
    ORDER_CANCEL_ERROR(5014, "当前订单状态无法撤销！", "The current order status cannot be revoked！", "當前訂單狀態無法撤銷！"),
    ORDER_USERID_ERROR(5015, "您与该订单并无交易关系！", "You have no transaction relationship with this order！", "您與該訂單並無交易關系！"),
    CLOSED(5016, "休市中！", "", ""),
    PUBLISH_ORDER_WALLET_ERROR(5017, "操作失败，钱包异常！", "Operation failed, wallet abnormal！", "操作失敗，錢包異常！"),
    TRADING_ON_NULL(5018, "当前平台不支持该代币交易！", "The current platform does not support this token transaction！", "當前平臺不支持該代幣交易！"),
    NET_ERROR(5019, "服务器繁忙，请稍后重试！", "The server is busy, please try again later!", "伺服器繁忙，請稍後重試！"),
    QUANTIZED_CLOSING(5020, "服务器正在调整，请稍后再试！", "The server is adjusting, please try again later!", "服務器正在調整，請稍後再試！"),
    ;

    @Getter
    private int code;
    @Getter
    private String cnmsg;
    @Getter
    private String enMsg;
    @Getter
    private String hkMsg;

    CctEnums(int code, String cnmsg, String enMsg, String hkMsg) {
        this.code = code;
        this.cnmsg = cnmsg;
        this.enMsg = enMsg;
        this.hkMsg = hkMsg;
    }
}
