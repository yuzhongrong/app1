package com.blockchain.common.base.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * GasDTO 手续费公用类
 */
@Data
public class GasDTO extends BaseDTO {
    private BigDecimal gasPrice; // 手续费
    private String gasTokenType; // 币种识别标识
    private String gasTokenSymbol; // 币种名称
    private String gasTokenName; // 币种名称
    private BigDecimal minWdAmount; // 最小提现余额
}