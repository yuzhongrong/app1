package com.blockchain.server.tron.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 预支付返回的参数据DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrepaymentInsertOutDTO {
    String tradeNo; // 商户订单号
    String prePayId; // 预支付ID
    String coinAddress; // 数字货币地址
    String coinName;    // 数字货币名称
    String amount;  // 预支付金额
    long timestamp;   // 下单成功时间
}
