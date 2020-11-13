package com.blockchain.server.eos.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;
import java.util.Date;

/**
 * Token 数据传输类
 * @date 2018-11-05 15:10:47
 * @version 1.0
 */
@Table(name = "dapp_eos_token")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Token extends BaseModel {
	@Id
	@Column(name = "token_name")
	private String tokenName;
	@Column(name = "token_symbol")
	private String tokenSymbol;
	@Column(name = "issue_time")
	private Date issueTime;
	@Column(name = "total_supply")
	private BigInteger totalSupply;
	@Column(name = "total_circulation")
	private String totalCirculation;
	@Column(name = "descr")
	private String descr;


}