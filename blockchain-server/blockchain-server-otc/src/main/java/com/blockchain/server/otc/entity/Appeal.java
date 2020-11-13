package com.blockchain.server.otc.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Appeal 数据传输类
 *
 * @version 1.0
 * @date 2019-04-18 15:54:26
 */
@Table(name = "otc_appeal")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appeal extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "order_number")
    private String orderNumber;
    @Column(name = "status")
    private String status;
    @Column(name = "create_time")
    private java.util.Date createTime;
    @Column(name = "modify_time")
    private java.util.Date modifyTime;

}