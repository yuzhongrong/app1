package com.blockchain.server.quantized.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author: Liusd
 * @create: 2019-04-19 16:43
 * CREATE TABLE `pc_quantized_order` (
 *   `id` bigint(50) NOT NULL COMMENT '订单ID',
 *   `amount` varchar(50) DEFAULT NULL COMMENT '订单数量',
 *   `canceled_at` bigint(20) DEFAULT NULL COMMENT '订单撤销时间',
 *   `created_at` bigint(20) DEFAULT NULL COMMENT '订单创建时间',
 *   `field_amount` varchar(50) DEFAULT NULL COMMENT '已成交数量',
 *   `field_cash_amount` varchar(50) DEFAULT NULL COMMENT '已成交总金额',
 *   `field_fees` varchar(50) DEFAULT NULL COMMENT '已成交手续费（买入为币，卖出为钱）',
 *   `finished_at` varchar(50) DEFAULT NULL COMMENT '订单变为终结态的时间，不是成交时间，包含“已撤单”状态',
 *   `price` varchar(50) DEFAULT NULL COMMENT '订单价格',
 *   `source` varchar(50) DEFAULT NULL COMMENT '订单来源',
 *   `state` varchar(50) DEFAULT NULL COMMENT '订单状态',
 *   `symbol` varchar(50) DEFAULT NULL COMMENT '交易对',
 *   `type` varchar(50) DEFAULT NULL COMMENT '订单类型',
 *   `user_id` varchar(0) DEFAULT NULL COMMENT '业务用户id',
 *   PRIMARY KEY (`id`)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 **/
@Table(name = "pc_quantized_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuantizedOrder {

    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "amount")
    private String amount;
    @Column(name = "canceled_at")
    private Long canceledAt;
    @Column(name = "created_at")
    private Long createdAt;
    @Column(name = "field_amount")
    private String fieldAmount;
    @Column(name = "field_cash_amount")
    private String fieldCashAmount;
    @Column(name = "field_fees")
    private String fieldFees;
    @Column(name = "finished_at")
    private Long finishedAt;
    @Column(name = "price")
    private String price;
    @Column(name = "source")
    private String source;
    @Column(name = "state")
    private String state;
    @Column(name = "symbol")
    private String symbol;
    @Column(name = "type")
    private String type;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "cct_id")
    private String cctId;
}
