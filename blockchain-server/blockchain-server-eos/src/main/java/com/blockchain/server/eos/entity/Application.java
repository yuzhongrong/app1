package com.blockchain.server.eos.entity;

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
@Table(name = "dapp_eos_application")
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