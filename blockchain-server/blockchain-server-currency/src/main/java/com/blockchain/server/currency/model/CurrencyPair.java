package com.blockchain.server.currency.model;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "dapp_currency_pair")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyPair extends BaseModel {

	@Id
	@Column(name = "currency_pair")
	private String currencyPair;
	@Column(name = "status")
	private Byte status;
	@Column(name = "order_by")
	private String orderBy;
	@Column(name = "is_home")
	private Byte isHome;
	@Column(name = "is_cct")
	private Byte isCct;

}