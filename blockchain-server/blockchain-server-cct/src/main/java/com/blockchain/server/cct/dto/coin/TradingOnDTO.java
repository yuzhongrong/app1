package com.blockchain.server.cct.dto.coin;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TradingOnDTO {
    private String coinName;
    private String unitName;
    private Integer coinDecimals;
    private Integer unitDecimals;
    private BigDecimal newestPrice;
}
