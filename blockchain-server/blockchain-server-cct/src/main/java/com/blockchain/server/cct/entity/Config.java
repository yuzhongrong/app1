package com.blockchain.server.cct.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Config 数据传输类
 *
 * @version 1.0
 * @date 2019-02-15 17:38:07
 */
@Table(name = "app_cct_config")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Config extends BaseModel {
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
    private java.util.Date createTime;
    @Column(name = "modify_time")
    private java.util.Date modifyTime;

}