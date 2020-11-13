package com.blockchain.server.currency.model;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "dapp_currency")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Currency extends BaseModel {

	@Id
	@Column(name = "currency_name")
	private String currencyName;
	@Column(name = "currency_name_cn")
	private String currencyNameCn;
	@Column(name = "currency_name_en")
	private String currencyNameEn;
	@Column(name = "currency_name_hk")
	private String currencyNameHk;
	@Column(name = "issue_time")
	private String issueTime;
	@Column(name = "total_supply")
	private String totalSupply;
	@Column(name = "total_circulation")
	private String totalCirculation;
	@Column(name = "descr_cn")
	private String descrCn;
	@Column(name = "descr_en")
	private String descrEn;
	@Column(name = "descr_hk")
	private String descrHk;
	@Column(name = "ico_amount")
	private String icoAmount;
	@Column(name = "white_paper")
	private String whitePaper;
	@Column(name = "official_website")
	private String officialWebsite;
	@Column(name = "block_url")
	private String blockUrl;
	@Column(name = "status")
	private String status;
	@Column(name = "order_by")
	private String orderBy;

}