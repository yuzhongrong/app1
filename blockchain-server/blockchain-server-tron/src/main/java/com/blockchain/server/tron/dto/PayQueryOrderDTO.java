package com.blockchain.server.tron.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单查询返回的数据DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayQueryOrderDTO {

    String tradeNo;	//商户单号
    String prePayId;	//预支付Id
    String coinAddress;	//数字货币地址
    String coinName;	//数字货币名称
    String amount;	//支付金额，单位是数字货币的最小单位
    long timestamp;	//下单成功时间（单位毫秒）
    PayInsertOutDTO payData;	//用户有支付且成功时为支付信息;



}
