package com.blockchain.server.databot.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * MatchConfig 数据传输类
 *
 * @version 1.0
 * @date 2019-06-25 13:52:32
 */
@Table(name = "bot_match_config")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchConfig extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "coin_name")
    private String coinName;
    @Column(name = "unit_name")
    private String unitName;
    @Column(name = "min_price")
    private BigDecimal minPrice;
    @Column(name = "max_price")
    private BigDecimal maxPrice;
    @Column(name = "min_percent")
    private BigDecimal minPercent;
    @Column(name = "max_percent")
    private BigDecimal maxPercent;
    @Column(name = "price_type")
    private String priceType;
    @Column(name = "status")
    private String status;
    @Column(name = "create_time")
    private java.util.Date createTime;
    @Column(name = "modify_time")
    private java.util.Date modifyTime;

}