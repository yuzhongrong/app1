package com.blockchain.server.teth.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;

/**
 * EthBlockNumber 数据传输类
 *
 * @version 1.0
 * @date 2019-02-16 15:44:06
 */
@Table(name = "dapp_teth_block_number")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EthBlockNumber extends BaseModel {
    @Id
    @Column(name = "block_number")
    private BigInteger blockNumber;
    @Column(name = "create_time")
    private java.util.Date createTime;
    @Column(name = "update_time")
    private java.util.Date updateTime;
    @Column(name = "status")
    private char status;
}