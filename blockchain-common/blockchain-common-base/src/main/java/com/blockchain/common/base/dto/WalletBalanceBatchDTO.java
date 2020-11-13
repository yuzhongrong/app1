package com.blockchain.common.base.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: qinhui
 * @date: 2020/6/2 15:53
 */
@Data
public class WalletBalanceBatchDTO{
    private String userOpenId;
    private String tokenSymbol;
    private BigDecimal freeBalance;
    private BigDecimal freezeBalance;
    private String walletType;

}
