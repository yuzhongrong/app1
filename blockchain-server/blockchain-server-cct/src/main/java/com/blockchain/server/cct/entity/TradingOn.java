package com.blockchain.server.cct.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * AppCctTradingRecord 数据传输类
 *
 * @version 1.0
 * @date 2019-03-06 11:53:27
 */
@Table(name = "pc_quantized_trading_on")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradingOn extends BaseModel {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "coin_name")
    private String coinName;
    @Column(name = "unit_name")
    private String unitName;
    @Column(name = "state")
    private String state;
    @Column(name = "create_time")
    private Date createTime;

}