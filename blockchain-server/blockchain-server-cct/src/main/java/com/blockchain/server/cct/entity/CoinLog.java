package com.blockchain.server.cct.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CoinLog 数据传输类
 *
 * @version 1.0
 * @date 2019-02-15 17:38:07
 */
@Table(name = "pc_cct_coin_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoinLog extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "sys_user_id")
    private String sysUserId;
    @Column(name = "ip_address")
    private String ipAddress;
    @Column(name = "coin_name")
    private String coinName;
    @Column(name = "unit_name")
    private String unitName;
    @Column(name = "coin_decimals")
    private String coinDecimals;
    @Column(name = "unit_decimals")
    private String unitDecimals;
    @Column(name = "type")
    private String type;
    @Column(name = "tag")
    private String tag;
    @Column(name = "create_time")
    private java.util.Date createTime;

}