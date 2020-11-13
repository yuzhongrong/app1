package com.blockchain.common.base.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/***
 * 交易盘口列表DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketDTO {
    private BigDecimal unitPrice; //单价
    private BigDecimal totalNum; //总数量
    private BigDecimal totalLastNum; //总剩余数量
    private String unitName; //二级货币
    private String coinName; //基本货币
    private String tradingType; //交易方向
}
