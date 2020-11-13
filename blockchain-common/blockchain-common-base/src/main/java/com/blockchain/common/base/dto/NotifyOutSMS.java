package com.blockchain.common.base.dto;

import lombok.Data;

/**
 * 用户提现短信通知
 */
@Data
public class NotifyOutSMS extends BaseDTO {
    //用户id
    private String userId;
    //金额
    private String amount;
    //币种名称
    private String coin;
}
