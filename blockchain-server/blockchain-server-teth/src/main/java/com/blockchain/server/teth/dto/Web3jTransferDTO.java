package com.blockchain.server.teth.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Web3jTransferDTO {
    String hash;             // 转账生成的标识hash值
    String fromAddr;         // 支付方钱包地址
    String toAddr;           // 收款方钱包地址
    String tokenAddr;          // 代币地址
    BigDecimal gasPrice;        //旷工费用[需除以18位小数]
    BigDecimal amount;          //转账金额[需除以代币小数位数]
    Boolean state;          //交易状态
}
