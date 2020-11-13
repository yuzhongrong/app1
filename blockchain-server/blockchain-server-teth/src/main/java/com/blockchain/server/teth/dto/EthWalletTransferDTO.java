package com.blockchain.server.teth.dto;

import lombok.Data;

/**
 * EthWalletTransfer 数据传输类
 *
 * @version 1.0
 * @date 2019-02-16 15:44:06
 */
@Data
public class EthWalletTransferDTO {
    private String id;
    private String hash;
    private String fromAddr;
    private String toAddr;
    private String amount;
    private String tokenAddr;
    private String tokenSymbol;
    private String gasPrice;
    private String gasTokenType;
    private String gasTokenName;
    private String gasTokenSymbol;
    private String transferType;
    private Integer status;
    private String remark;
    private java.util.Date createTime;
    private java.util.Date updateTime;

}