package com.blockchain.server.quantized.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * AppCctTradingRecord 数据传输类
 *
 * @version 1.0
 * @date 2019-03-06 11:53:27
 */
@Table(name = "pc_quantized_order_err")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderErr extends BaseModel {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "symbol")
    private String symbol;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "order_type")
    private String orderType;
    @Column(name = "msg")
    private String msg;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "cct_id")
    private String cctId;

}