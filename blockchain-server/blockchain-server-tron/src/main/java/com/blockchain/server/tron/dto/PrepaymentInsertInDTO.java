package com.blockchain.server.tron.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 预支付接收参数据DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrepaymentInsertInDTO {
    String appId;   //应用标识
    String appSecret;   // 安全码
    String coinAddress; // 数字货币地址
    String coinName;    // 数字货币名称
    String amount;  // 预支付金额
    String notifyUrl;   // 通知地址
    String tradeNo; // 商户订单号
    String openId; // 用户id
    String sign;    // 签名

}
