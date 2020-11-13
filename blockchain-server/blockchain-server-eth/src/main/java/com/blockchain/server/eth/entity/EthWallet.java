package com.blockchain.server.eth.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * EthWallet 数据传输类
 * @date 2019-02-16 15:44:06
 * @version 1.0
 */
@Table(name = "dapp_eth_wallet")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EthWallet extends BaseModel {
	@Column(name = "addr")
	private String addr;
	@Column(name = "token_addr")
	private String tokenAddr;
	@Column(name = "user_open_id")
	private String userOpenId;
	@Column(name = "token_symbol")
	private String tokenSymbol;
	@Column(name = "token_decimals")
	private Integer tokenDecimals;
	@Column(name = "balance")
	private BigDecimal balance;
	@Column(name = "free_balance")
	private BigDecimal freeBalance;
	@Column(name = "freeze_balance")
	private BigDecimal freezeBalance;
	@Column(name = "create_time")
	private java.util.Date createTime;
	@Column(name = "update_time")
	private java.util.Date updateTime;
	@Column(name = "wallet_type")
	private String walletType;

}