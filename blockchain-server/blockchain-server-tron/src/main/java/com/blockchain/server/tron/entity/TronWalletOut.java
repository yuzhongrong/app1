package com.blockchain.server.tron.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TronWalletOut 提现资金钱包
 * @date 2018-11-05 15:10:47
 * @version 1.0
 */
@Table(name = "dapp_tron_wallet_out")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TronWalletOut extends BaseModel {
	@Id
	@Column(name = "id")
	private String id;
	@Column(name = "addr")
	private String addr;
	@Column(name = "hex_addr")
	private String hexAddr;
	@Column(name = "token_addr")
	private String tokenAddr;
	@Column(name = "token_symbol")
	private String tokenSymbol;
	@Column(name = "token_decimal")
	private String tokenDecimal;
	@Column(name = "private_key")
	private String privateKey;
	@Column(name = "password")
	private String password;
	@Column(name = "remark")
	private String remark;
	@Column(name = "status")
	private String status;

}