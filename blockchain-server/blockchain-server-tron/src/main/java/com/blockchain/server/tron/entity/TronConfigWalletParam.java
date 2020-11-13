package com.blockchain.server.tron.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Apply 数据传输类
 * @date 2018年12月5日10:05:13
 * @version 1.0
 */
@Table(name = "config_wallet_param")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TronConfigWalletParam extends BaseModel {
	@Id
	@Column(name = "id")
	private String id;
	@Column(name = "module_type")
	private String moduleType;
	@Column(name = "param_name")
	private String paramName;
	@Column(name = "param_value")
	private String paramValue;
	@Column(name = "param_descr")
	private String paramDescr;
	@Column(name = "status")
	private String status;
}