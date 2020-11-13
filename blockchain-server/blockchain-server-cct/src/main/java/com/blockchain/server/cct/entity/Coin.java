package com.blockchain.server.cct.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Coin 数据传输类
 *
 * @version 1.0
 * @date 2019-02-15 17:38:07
 */
@Table(name = "app_cct_coin")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coin extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "coin_name")
    private String coinName;
    @Column(name = "unit_name")
    private String unitName;
    @Column(name = "coin_decimals")
    private Integer coinDecimals;
    @Column(name = "unit_decimals")
    private Integer unitDecimals;
    @Column(name = "coin_net")
    private String coinNet;
    @Column(name = "unit_net")
    private String unitNet;
    @Column(name = "rank")
    private Integer rank;
    @Column(name = "status")
    private String status;
    @Column(name = "tag")
    private String tag;
    @Column(name = "create_time")
    private java.util.Date createTime;
    @Column(name = "modify_time")
    private java.util.Date modifyTime;

}