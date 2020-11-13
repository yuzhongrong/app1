package com.blockchain.server.eos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Wallet 数据传输类
 * @date 2018-11-05 15:10:47
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletDTO {
	private Integer id;
	private String tokenName;
	private String userOpenId;
	private String tokenSymbol;
	private BigDecimal balance;
	private BigDecimal freeBalance;
	private BigDecimal freezeBalance;
	private java.util.Date createTime;
	private java.util.Date updateTime;
	private String walletType;

}