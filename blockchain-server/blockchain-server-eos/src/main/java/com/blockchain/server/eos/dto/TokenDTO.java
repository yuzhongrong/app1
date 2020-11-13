package com.blockchain.server.eos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;

/**
 * TokentDTO 数据传输类（以太坊代币配置表）
 * @date 2018-11-05 15:10:47
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO {
	private String tokenName;
	private String tokenSymbol;
	private Date issueTime;
	private String totalSupply;
	private String totalCirculation;
	private String descr;

}