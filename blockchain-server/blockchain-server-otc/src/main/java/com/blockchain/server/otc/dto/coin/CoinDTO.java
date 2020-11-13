package com.blockchain.server.otc.dto.coin;

import lombok.Data;

@Data
public class CoinDTO {
    private String coinName;
    private String unitName;
    private Integer coinDecimal;
    private Integer unitDecimal;
}
