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
 * Bill 数据传输类
 *
 * @version 1.0
 * @date 2019-04-15 10:37:31
 */
@Table(name = "otc_bill")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bill extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "record_number")
    private String recordNumber;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "freezebalance")
    private BigDecimal freezebalance;
    @Column(name = "freebalance")
    private BigDecimal freebalance;
    @Column(name = "bill_type")
    private String billType;
    @Column(name = "coin_name")
    private String coinName;
    @Column(name = "create_time")
    private java.util.Date createTime;

}