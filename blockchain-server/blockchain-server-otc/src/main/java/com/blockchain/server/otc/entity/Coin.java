package com.blockchain.server.otc.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Coin 数据传输类
 *
 * @version 1.0
 * @date 2019-04-15 10:37:31
 */
@Table(name = "otc_coin")
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
    @Column(name = "coin_net")
    private String coinNet;
    @Column(name = "coin_decimal")
    private Integer coinDecimal;
    @Column(name = "unit_decimal")
    private Integer unitDecimal;
    @Column(name = "coin_service_charge")
    private BigDecimal coinServiceCharge;
    @Column(name = "status")
    private String status;
    @Column(name = "rank")
    private String rank;
    @Column(name = "create_time")
    private java.util.Date createTime;
    @Column(name = "modify_time")
    private java.util.Date modifyTime;

}