package com.blockchain.common.base.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 钱包修改余额接口DTO
 */
@Data
public class WalletOrderDTO {
    String userId;
    String recordId; // 记录标识(可以为空)
    String tokenName; // 币种名称
    BigDecimal freeBalance; // 可用余额
    BigDecimal freezeBalance; // 冻结余额
    String walletType; // 钱包标识
}
