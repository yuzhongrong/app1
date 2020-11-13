package com.blockchain.server.btc.dto;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * BtcWalletTransferDTO 数据传输类
 *
 * @version 1.0
 * @date 2019-02-16 15:08:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BtcWalletTransferDTO extends BaseModel {
    private String id;
    private String hash;
    private String fromAddr;
    private String toAddr;
    private BigDecimal amount;
    private Integer tokenId;
    private String tokenSymbol;
    private BigDecimal gasPrice;
    private String gasTokenType;
    private String gasTokenName;
    private String gasTokenSymbol;
    private String transferType;
    private Integer status;
    private String remark;
    private java.util.Date createTime;
    private java.util.Date updateTime;

}