package com.blockchain.server.tron.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * TronToken 数据传输类
 * @date 2018-11-05 15:10:47
 * @version 1.0
 */
@Table(name = "dapp_tron_token")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TronToken extends BaseModel {
	@Id
	@Column(name = "token_addr")
	private String tokenAddr;
	@Column(name = "token_hex_addr")
	private String tokenHexAddr;
	@Column(name = "token_symbol")
	private String tokenSymbol;
	@Column(name = "token_decimal")
	private Integer tokenDecimal;
	@Column(name = "issue_time")
	private Date issueTime;
	@Column(name = "total_supply")
	private BigDecimal totalSupply;
	@Column(name = "total_circulation")
	private String totalCirculation;
	@Column(name = "descr")
	private String descr;


}