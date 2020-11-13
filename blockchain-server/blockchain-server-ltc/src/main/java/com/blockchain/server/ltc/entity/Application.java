package com.blockchain.server.ltc.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ApplicationDTO 数据传输类
 *
 * @version 1.0
 * @date 2019-02-16 15:08:16
 */
@Table(name = "dapp_ltc_application")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Application extends BaseModel {
    @Id
    @Column(name = "app_id")
    private String appId;
    @Column(name = "app_name")
    private String appName;

}