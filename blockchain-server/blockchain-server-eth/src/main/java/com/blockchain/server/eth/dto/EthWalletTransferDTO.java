package com.blockchain.server.eth.dto;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

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