package com.blockchain.server.ltc.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TokenDTO 数据传输类
 *
 * @version 1.0
 * @date 2019-02-16 15:08:16
 */
@Table(name = "dapp_ltc_token")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Token extends BaseModel {
    @Id
    @Column(name = "token_id")
    private Integer tokenId;
    @Column(name = "token_symbol")
    private String tokenSymbol;
    @Column(name = "issue_time")
    private java.util.Date issueTime;
    @Column(name = "total_supply")
    private String totalSupply;
    @Column(name = "total_circulation")
    private String totalCirculation;
    @Column(name = "descr")
    private String descr;

}