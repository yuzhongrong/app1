package com.blockchain.server.quantized.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author: Liusd
 * @create: 2019-04-19 16:47
 * CREATE TABLE `pc_quantized_order_filled` (
 *   `id` bigint(50) NOT NULL COMMENT '成交ID',
 *   `created_at` bigint(20) DEFAULT NULL COMMENT '成交时间',
 *   `filled_amount` varchar(50) DEFAULT NULL COMMENT '成交数量',
 *   `filled_fees` varchar(50) DEFAULT NULL COMMENT '成交手续费',
 *   `order_id` bigint(20) DEFAULT NULL COMMENT '订单ID',
 *   `price` varchar(50) DEFAULT NULL COMMENT '订单价格',
 *   `source` varchar(50) DEFAULT NULL COMMENT '订单来源',
 *   `symbol` varchar(50) DEFAULT NULL COMMENT '交易对',
 *   `type` varchar(50) DEFAULT NULL COMMENT '订单类型',
 *   PRIMARY KEY (`id`)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 **/
@Table(name = "pc_quantized_order_filled")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilledOrder {

    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "canceled_at")
    private Long canceledAt;
    @Column(name = "filled_amount")
    private String filledAmount;
    @Column(name = "filled_fees")
    private String filledFees;
    @Column(name = "price")
    private String price;
    @Column(name = "source")
    private String source;
    @Column(name = "symbol")
    private String symbol;
    @Column(name = "type")
    private String type;
}
