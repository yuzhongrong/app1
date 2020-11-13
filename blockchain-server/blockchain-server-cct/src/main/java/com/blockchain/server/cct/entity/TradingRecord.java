package com.blockchain.server.cct.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * TradingRecord 数据传输类
 *
 * @version 1.0
 * @date 2019-02-16 15:08:15
 */
@Table(name = "app_cct_trading_record")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradingRecord extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "maker_id")
    private String makerId;
    @Column(name = "taker_id")
    private String takerId;
    @Column(name = "maker_price")
    private BigDecimal makerPrice;
    @Column(name = "taker_price")
    private BigDecimal takerPrice;
    @Column(name = "trading_num")
    private BigDecimal tradingNum;
    @Column(name = "trading_type")
    private String tradingType;
    @Column(name = "coin_name")
    private String coinName;
    @Column(name = "unit_name")
    private String unitName;
    @Column(name = "create_time")
    private java.util.Date createTime;

}