package com.blockchain.server.tron.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 支付接口返回的数据DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayInsertOutDTO {
    String tradeNo; // 商户订单号
    String transactionId; // 支付Id
    String coinAddress; // 数字货币地址
    String coinName;    // 数字货币名称
    String amount;  // 预支付金额
    long timestamp;   // 下单成功时间
    String sign; // 签名
    String openId; // 用户ID
}
