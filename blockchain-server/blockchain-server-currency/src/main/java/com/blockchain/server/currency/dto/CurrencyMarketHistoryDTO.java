package com.blockchain.server.currency.dto;

import com.blockchain.common.base.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyMarketHistoryDTO extends BaseDTO{
    private BigDecimal makerPrice;
    private BigDecimal tradingNum;
    private String coinName;
    private String unitName;
    private String createTime;
    private String tradingType;
}