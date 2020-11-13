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
 * Order 数据传输类
 *
 * @version 1.0
 * @date 2019-04-15 10:37:32
 */
@Table(name = "otc_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "order_number")
    private String orderNumber;
    @Column(name = "ad_id")
    private String adId;
    @Column(name = "coin_name")
    private String coinName;
    @Column(name = "unit_name")
    private String unitName;
    @Column(name = "buy_user_id")
    private String buyUserId;
    @Column(name = "buy_status")
    private String buyStatus;
    @Column(name = "sell_user_id")
    private String sellUserId;
    @Column(name = "sell_status")
    private String sellStatus;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "turnover")
    private BigDecimal turnover;
    @Column(name = "charge_ratio")
    private BigDecimal chargeRatio;
    @Column(name = "order_type")
    private String orderType;
    @Column(name = "order_status")
    private String orderStatus;
    @Column(name = "order_pay_type")
    private String orderPayType;
    @Column(name = "create_time")
    private java.util.Date createTime;
    @Column(name = "modify_time")
    private java.util.Date modifyTime;

}