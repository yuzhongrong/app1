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
 * TradingDetail 数据传输类
 *
 * @version 1.0
 * @date 2019-02-16 15:08:15
 */
@Table(name = "app_cct_trading_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradingDetail extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "record_id")
    private String recordId;
    @Column(name = "publish_id")
    private String publishId;
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    @Column(name = "real_amount")
    private BigDecimal realAmount;
    @Column(name = "unit_price")
    private BigDecimal unitPrice;
    @Column(name = "trading_num")
    private BigDecimal tradingNum;
    @Column(name = "charge_ratio")
    private BigDecimal chargeRatio;
    @Column(name = "service_charge")
    private BigDecimal serviceCharge;
    @Column(name = "trading_type")
    private String tradingType;
    @Column(name = "coin_name")
    private String coinName;
    @Column(name = "create_time")
    private java.util.Date createTime;

}