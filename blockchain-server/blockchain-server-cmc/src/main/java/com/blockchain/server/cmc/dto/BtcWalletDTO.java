package com.blockchain.server.cmc.dto;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * BtcWalletDTO 数据传输类
 *
 * @version 1.0
 * @date 2019-02-16 15:08:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BtcWalletDTO extends BaseModel {
    private String addr;
    private Integer tokenId;
    private String userOpenId;
    private String tokenSymbol;
    private BigDecimal balance;
    private BigDecimal freeBalance;
    private BigDecimal freezeBalance;
    private java.util.Date createTime;
    private java.util.Date updateTime;
    private String walletType;

}