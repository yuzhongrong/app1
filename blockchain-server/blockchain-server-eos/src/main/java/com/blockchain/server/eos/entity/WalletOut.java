package com.blockchain.server.eos.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * WalletOut 提现资金钱包
 * @date 2018-11-05 15:10:47
 * @version 1.0
 */
@Table(name = "dapp_eos_wallet_out")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletOut extends BaseModel {
	@Id
	@Column(name = "id")
	private String id;
	@Column(name = "account_name")
	private String accountName;
	@Column(name = "token_name")
	private String tokenName;
	@Column(name = "token_symbol")
	private String tokenSymbol;
	@Column(name = "private_key")
	private String privateKey;
	@Column(name = "password")
	private String password;
	@Column(name = "remark")
	private String remark;
	@Column(name = "status")
	private String status;

}