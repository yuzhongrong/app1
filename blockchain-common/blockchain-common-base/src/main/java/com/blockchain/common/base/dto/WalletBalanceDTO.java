package com.blockchain.common.base.dto;

import com.blockchain.common.base.entity.BaseModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 获取钱包余额DTO
 */
@Data
public class WalletBalanceDTO extends BaseModel {
    public String tokenSymbol;
    public BigDecimal freeBalance;
    public BigDecimal freezeBalance;
    public String walletType;

}