package com.blockchain.server.currency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * \* <p>Desciption:</p>
 * \* CreateTime: 2019/2/26 11:30
 * \* User: XianChaoWei
 * \* Version: V1.0
 * \
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyDTO {
    private String currencyName;
    private String currencyFullName;
    private String issueTime;
    private String totalSupply;
    private String totalCirculation;
    private String icoAmount;
    private String whitePaper;
    private String officialWebsite;
    private String blockUrl;
    private String descr;
}
