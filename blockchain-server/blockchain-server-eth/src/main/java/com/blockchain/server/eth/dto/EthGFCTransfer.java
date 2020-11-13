package com.blockchain.server.eth.dto;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * @author: qinhui
 * @date: 2020/8/14 13:57
 */
@Data
public class EthGFCTransfer {
       private String orderId; //订单id
       private String account;  //交易所账号
       private BigDecimal amount;   //金额
       private String transferType; //转账类型
       private String status;   //转账状态
}
