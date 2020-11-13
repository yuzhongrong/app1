package com.blockchain.server.eth.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * EthApplication 数据传输类
 * @date 2019-02-16 15:44:06
 * @version 1.0
 */
@Table(name = "dapp_eth_application")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EthApplication extends BaseModel {
	@Column(name = "app_id")
	private String appId;
	@Column(name = "app_name")
	private String appName;

}