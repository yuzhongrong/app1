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
 * MarketFreeze 数据传输类
 *
 * @version 1.0
 * @date 2019-07-13 11:48:11
 */
@Table(name = "otc_market_freeze")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketFreeze extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "market_apply_id")
    private String marketApplyId;
    @Column(name = "coin_name")
    private String coinName;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "create_time")
    private java.util.Date createTime;
}