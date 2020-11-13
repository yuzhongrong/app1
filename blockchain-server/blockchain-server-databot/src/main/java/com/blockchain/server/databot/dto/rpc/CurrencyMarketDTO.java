package com.blockchain.server.databot.dto.rpc;

import com.blockchain.common.base.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyMarketDTO extends BaseDTO implements Comparable<CurrencyMarketDTO>{

    private String currencyPair;
    private BigDecimal amount;
    private Long timestamp;
    private float percent;
    private double usdAmount;
    private double cnyAmount;
    private double hkdAmount;
    private double eurAmount;
    private BigDecimal lowest;//最低
    private BigDecimal highest;//最高
    private BigDecimal open;//开盘
    private BigDecimal total;//成交量

    @Override
    public int compareTo(CurrencyMarketDTO o) {
        return (int)(10000*o.percent - 10000*this.percent);
    }
}