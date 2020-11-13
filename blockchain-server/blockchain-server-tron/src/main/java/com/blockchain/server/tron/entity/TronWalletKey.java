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
 * Apply 数据传输类
 * @date 2018年12月5日10:05:13
 * @version 1.0
 */
@Table(name = "dapp_tron_wallet_key")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TronWalletKey extends BaseModel {
	@Id
	@Column(name = "user_open_id")
	private String userOpenId;
	@Column(name = "addr")
	private String addr;
	@Column(name = "hex_addr")
	private String hexAddr;
	@Column(name = "private_key")
	private String privateKey;
	@Column(name = "keystore")
	private String keystore;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "update_time")
	private Date updateTime;
}