package com.blockchain.server.ltc.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * WalletDTO 数据传输类
 *
 * @version 1.0
 * @date 2019-02-16 15:08:16
 */
@Table(name = "dapp_ltc_wallet")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wallet extends BaseModel {
    @Column(name = "addr")
    private String addr;
    @Column(name = "token_id")
    private Integer tokenId;
    @Column(name = "user_open_id")
    private String userOpenId;
    @Column(name = "token_symbol")
    private String tokenSymbol;
    @Column(name = "balance")
    private BigDecimal balance;
    @Column(name = "free_balance")
    private BigDecimal freeBalance;
    @Column(name = "freeze_balance")
    private BigDecimal freezeBalance;
    @Column(name = "create_time")
    private java.util.Date createTime;
    @Column(name = "update_time")
    private java.util.Date updateTime;
    @Column(name = "wallet_type")
    private String walletType;

}