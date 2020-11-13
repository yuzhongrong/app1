package com.blockchain.server.user.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author huangxl
 * @create 2019-02-25 17:45
 */
@Data
@Table(name = "dapp_u_config")
public class Config {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "data_tag")
    private String dataTag;
    @Column(name = "data_key")
    private String dataKey;
    @Column(name = "data_value")
    private String dataValue;
    @Column(name = "data_status")
    private String dataStatus;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "modify_time")
    private Date modifyTime;
}
