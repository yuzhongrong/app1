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
 * ad 数据传输类
 *
 * @version 1.0
 * @date 2019-04-15 10:37:31
 */
@Table(name = "otc_ad")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ad extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "ad_number")
    private String adNumber;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "total_num")
    private BigDecimal totalNum;
    @Column(name = "last_num")
    private BigDecimal lastNum;
    @Column(name = "coin_name")
    private String coinName;
    @Column(name = "unit_name")
    private String unitName;
    @Column(name = "min_limit")
    private BigDecimal minLimit;
    @Column(name = "max_limit")
    private BigDecimal maxLimit;
    @Column(name = "charge_ratio")
    private BigDecimal chargeRatio;
    @Column(name = "ad_pay")
    private String adPay;
    @Column(name = "ad_type")
    private String adType;
    @Column(name = "ad_status")
    private String adStatus;
    @Column(name = "ad_remark")
    private String adRemark;
    @Column(name = "create_time")
    private java.util.Date createTime;
    @Column(name = "modify_time")
    private java.util.Date modifyTime;

}