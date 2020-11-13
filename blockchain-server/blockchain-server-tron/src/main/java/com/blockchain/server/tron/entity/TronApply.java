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
@Table(name = "dapp_tron_wallet_apply")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TronApply extends BaseModel {
	@Id
	@Column(name = "id")
	private String id;
	@Column(name = "app_id")
	private String appId;
	@Column(name = "app_secret")
	private String appSecret;
}