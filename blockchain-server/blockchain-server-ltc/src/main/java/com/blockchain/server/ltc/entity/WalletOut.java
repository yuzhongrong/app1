package com.blockchain.server.ltc.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * WalletOutDTO 数据传输类
 *
 * @version 1.0
 * @date 2019-02-16 15:08:16
 */
@Table(name = "dapp_ltc_wallet_out")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletOut extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "addr")
    private String addr;
    @Column(name = "token_id")
    private Integer tokenId;
    @Column(name = "token_symbol")
    private String tokenSymbol;
    @Column(name = "private_key")
    private String privateKey;
    @Column(name = "password")
    private String password;
    @Column(name = "remark")
    private String remark;

}