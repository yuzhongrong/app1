package com.blockchain.server.databot.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CurrencyConfig 数据传输类
 *
 * @version 1.0
 * @date 2019-06-03 11:37:01
 */
@Table(name = "bot_currency_config")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyConfig extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "currency_pair")
    private String currencyPair;
    @Column(name = "status")
    private String status;
    @Column(name = "price_type")
    private String priceType;
    @Column(name = "k_change_percent")
    private Float kChangePercent;
    @Column(name = "k_max_change_percent")
    private Float kMaxChangePercent;
    @Column(name = "k_day_total_amount")
    private Float kDayTotalAmount;
    @Column(name = "k_max_price")
    private Float kMaxPrice;
    @Column(name = "k_min_price")
    private Float kMinPrice;
    @Column(name = "buy_max_price")
    private Float buyMaxPrice;
    @Column(name = "buy_min_price")
    private Float buyMinPrice;
    @Column(name = "buy_price_percent")
    private Float buyPricePercent;
    @Column(name = "buy_total_amount")
    private Float buyTotalAmount;
    @Column(name = "sell_max_price")
    private Float sellMaxPrice;
    @Column(name = "sell_min_price")
    private Float sellMinPrice;
    @Column(name = "sell_price_percent")
    private Float sellPricePercent;
    @Column(name = "sell_total_amount")
    private Float sellTotalAmount;
    @Column(name = "create_time")
    private java.util.Date createTime;
    @Column(name = "modify_time")
    private java.util.Date modifyTime;
}