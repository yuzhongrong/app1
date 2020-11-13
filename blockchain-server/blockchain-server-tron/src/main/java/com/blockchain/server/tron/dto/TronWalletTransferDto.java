package com.blockchain.server.tron.dto;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
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
public class TronWalletTransferDto extends BaseModel {
    private String id;
    private String hash;
    private String fromAddr;
    private String fromHexAddr;
    private String toAddr;
    private String toHexAddr;
    private BigDecimal amount;
    private String tokenAddr;
    private String tokenSymbol;
    private BigDecimal gasPrice;
    private String gasTokenType;
    private String gasTokenName;
    private String gasTokenSymbol;
    private String transferType;
    private Integer status;
    private String remark;
    private Date createTime;
    private Date updateTime;

}