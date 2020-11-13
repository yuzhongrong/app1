package com.blockchain.server.cct.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * AppCctBill 数据传输类
 *
 * @version 1.0
 * @date 2019-02-15 17:38:07
 */
@Table(name = "app_cct_bill")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bill extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "coin_name")
    private String coinName;
    @Column(name = "plan_money")
    private String planMoney;
    @Column(name = "real_money")
    private String realMoney;
    @Column(name = "service_charge")
    private String serviceCharge;
    @Column(name = "tag")
    private String tag;
    @Column(name = "create_time")
    private java.util.Date createTime;

}