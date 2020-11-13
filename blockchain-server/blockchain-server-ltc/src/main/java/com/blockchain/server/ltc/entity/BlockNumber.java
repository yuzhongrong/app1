package com.blockchain.server.ltc.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BlockNumberDTO 数据传输类
 *
 * @version 1.0
 * @date 2019-02-16 15:08:16
 */
@Table(name = "dapp_ltc_block_number")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlockNumber extends BaseModel {
    @Id
    @Column(name = "block_number")
    private Integer blockNumber;
    @Column(name = "create_time")
    private java.util.Date createTime;
    @Column(name = "update_time")
    private java.util.Date updateTime;
    @Column(name = "status")
    private String status;

}