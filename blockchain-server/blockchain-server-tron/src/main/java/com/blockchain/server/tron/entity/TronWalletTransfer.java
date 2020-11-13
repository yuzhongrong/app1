package com.blockchain.server.tron.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
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
@Table(name = "dapp_tron_wallet_transfer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TronWalletTransfer extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "hash")
    private String hash;
    @Column(name = "from_hex_addr")
    private String fromHexAddr;
    @Column(name = "to_hex_addr")
    private String toHexAddr;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "token_addr")
    private String tokenAddr;
    @Column(name = "token_symbol")
    private String tokenSymbol;
    @Column(name = "gas_price")
    private BigDecimal gasPrice;
    @Column(name = "gas_token_type")
    private String gasTokenType;
    @Column(name = "gas_token_name")
    private String gasTokenName;
    @Column(name = "gas_token_symbol")
    private String gasTokenSymbol;
    @Column(name = "transfer_type")
    private String transferType;
    @Column(name = "status")
    private Integer status;
    @Column(name = "remark")
    private String remark;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;

}