package com.blockchain.server.otc.dto.coin;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CoinServiceChargeDTO {
    private String coinName;
    private String unitName;
    private BigDecimal coinServiceCharge;
    private String rank;
}
