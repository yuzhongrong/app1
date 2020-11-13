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
 * PublishOrder 数据传输类
 *
 * @version 1.0
 * @date 2019-02-15 17:38:07
 */
@Table(name = "app_cct_publish_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublishOrder extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "unit_price")
    private BigDecimal unitPrice;
    @Column(name = "total_num")
    private BigDecimal totalNum;
    @Column(name = "last_num")
    private BigDecimal lastNum;
    @Column(name = "total_turnover")
    private BigDecimal totalTurnover;
    @Column(name = "last_turnover")
    private BigDecimal lastTurnover;
    @Column(name = "coin_name")
    private String coinName;
    @Column(name = "unit_name")
    private String unitName;
    @Column(name = "order_status")
    private String orderStatus;
    @Column(name = "order_type")
    private String orderType;
    @Column(name = "publish_type")
    private String publishType;
    @Column(name = "version")
    private int version;
    @Column(name = "create_time")
    private java.util.Date createTime;
    @Column(name = "modify_time")
    private java.util.Date modifyTime;

}