package com.blockchain.server.tron.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 退款返回参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RechargeOutDTO {
    String openId; // 用户ID
    String tradeNo; // 商户订单号
    String coinAddress; // 数字货币地址
    String coinName;    // 数字货币名称
    String amount;  // 预支付金额
    long timestamp;  // 奖金发放成功时间（单位毫秒）
    String rechargeId;  // 奖金发放id

}
