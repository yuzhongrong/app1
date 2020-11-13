package com.blockchain.server.eth.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * EthToken 数据传输类
 *
 * @version 1.0
 * @date 2019-02-16 15:44:06
 */
@Table(name = "dapp_eth_token")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EthToken extends BaseModel {
    @Id
    @Column(name = "token_addr")
    private String tokenAddr;
    @Column(name = "token_symbol")
    private String tokenSymbol;
    @Column(name = "token_decimals")
    private Integer tokenDecimals;
    @Column(name = "issue_time")
    private java.util.Date issueTime;
    @Column(name = "total_supply")
    private String totalSupply;
    @Column(name = "total_circulation")
    private String totalCirculation;
    @Column(name = "descr")
    private String descr;
    @Column(name = "create_time")
    private java.util.Date createTime;
    @Column(name = "update_time")
    private java.util.Date updateTime;

}