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
public class CurrencyMarketKDTO extends BaseDTO {

    private BigDecimal open;//开盘
    private BigDecimal close;//收盘
    private Long openTime;//开盘
    private Long closeTime;//收盘
    private BigDecimal lowest;//最低
    private BigDecimal highest;//最高
    private BigDecimal total;//成交量
    private String date;
    private Long timestamp;

}