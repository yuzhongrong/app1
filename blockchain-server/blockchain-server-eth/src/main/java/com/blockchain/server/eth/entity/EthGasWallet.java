package com.blockchain.server.eth.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * EthWalletKey 数据传输类
 *
 * @version 1.0
 * @date 2019-02-16 15:44:06
 */
@Table(name = "dapp_eth_gas_wallet")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EthGasWallet extends BaseModel {
    @Id
    @Column(name = "addr")
    private String addr;
    @Column(name = "private_key")
    private String privateKey;
    @Column(name = "create_time")
    private java.util.Date createTime;
    @Column(name = "update_time")
    private java.util.Date updateTime;
}