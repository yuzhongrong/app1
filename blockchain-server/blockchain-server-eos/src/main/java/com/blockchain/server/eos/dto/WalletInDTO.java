package com.blockchain.server.eos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

/**
 * WalletIn 充值资金钱包
 * @date 2018-11-05 15:10:47
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletInDTO {
	private String id;
	private String accountName;
	private String tokenName;
	private String tokenSymbol;
	private String remark;
	private String status;
	private BigInteger blockNumber;
}