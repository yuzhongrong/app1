package com.blockchain.server.tron.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Inform 数据传输类
 *
 * @version 1.0
 * @date 2018年12月5日10:05:13
 */
@Table(name = "dapp_tron_wallet_inform")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TronInform extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "params_id")
    private String paramsId;
    @Column(name = "params_json")
    private String paramsJson;
    @Column(name = "url")
    private String url;
    @Column(name = "time")
    private Integer time;
    @Column(name = "status")
    private Integer status;
    @Column(name = "inform_type")
    private String informType;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
}