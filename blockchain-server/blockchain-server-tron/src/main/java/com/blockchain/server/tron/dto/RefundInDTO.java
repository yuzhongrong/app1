package com.blockchain.server.tron.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 退款接收参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefundInDTO {
    String appId;   //应用id
    String appSecret;   //应用密钥
    String tradeNo; // 商户订单号
    String coinAddress; // 数字货币地址
    String coinName;    // 数字货币名称
    String amount;  // 预支付金额
}
