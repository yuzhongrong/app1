package com.blockchain.server.otc.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DealStats 数据传输类
 *
 * @version 1.0
 * @date 2019-04-15 10:37:31
 */
@Table(name = "otc_deal_stats")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DealStats extends BaseModel {
    @Id
    @Column(name = "user_id")
    private String userId;
    @Column(name = "ad_trans_num")
    private Integer adTransNum;
    @Column(name = "ad_mark_num")
    private Integer adMarkNum;
    @Column(name = "order_sell_num")
    private Integer orderSellNum;
    @Column(name = "order_buy_num")
    private Integer orderBuyNum;
    @Column(name = "create_time")
    private java.util.Date createTime;
    @Column(name = "modify_time")
    private java.util.Date modifyTime;

}