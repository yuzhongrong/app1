package com.blockchain.server.eos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * WalletTransfert 用户钱包历史记录
 *
 * @version 1.0
 * @date 2018-11-05 15:10:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletTransferDTO {
    private String id;
    private String hash;
    private Integer fromId;
    private Integer toId;
    private String accountName;
    private BigDecimal amount;
    private String tokenName;
    private String tokenSymbol;
    private BigDecimal gasPrice;
    private String gasTokenType;
    private String gasTokenName;
    private String gasTokenSymbol;
    private String transferType;
    private Integer status;
    private String remark;
    private Date timestamp;

}