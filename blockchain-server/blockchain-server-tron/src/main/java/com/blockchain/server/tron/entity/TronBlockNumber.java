package com.blockchain.server.tron.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;
import java.util.Date;

/**
 * \* <p>Desciption:</p>
 * \* CreateTime: 2018/12/7 17:09
 * \* User: XianChaoWei
 * \* Version: V1.0
 * \
 */
@Table(name = "dapp_tron_block_number")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TronBlockNumber {

    @Id
    @Column(name = "block_number")
    private BigInteger blockNumber;
    @Column(name = "status")
    private Character status;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;

}
