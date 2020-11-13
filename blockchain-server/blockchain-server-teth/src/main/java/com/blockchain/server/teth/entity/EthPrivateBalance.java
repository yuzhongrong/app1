package com.blockchain.server.teth.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;

@Table(name = "dapp_teth_private_balance")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EthPrivateBalance extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "user_open_id")
    private String userOpenId;
    @Column(name = "addr")
    private String addr;
    @Column(name = "token_addr")
    private String tokenAddr;
    @Column(name = "token_symbol")
    private String tokenSymbol;
    @Column(name = "private_balance")
    private BigDecimal privateBalance;
    @Column(name = "release_balance")
    private BigDecimal releaseBalance;
    @Column(name = "wallet_type")
    private String walletType;
    @Column(name = "create_time")
    private java.util.Date createTime;
    @Column(name = "modify_time")
    private java.util.Date modifyTime;
}