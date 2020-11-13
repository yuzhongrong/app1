package com.blockchain.server.teth.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * EthTransferAuditing 数据传输类
 * @date 2019-02-16 15:44:06
 * @version 1.0
 */
@Table(name = "dapp_teth_transfer_auditing")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EthTransferAuditing extends BaseModel {
	@Column(name = "id")
	private String id;
	@Column(name = "sys_user_id")
	private String sysUserId;
	@Column(name = "ip_addr")
	private String ipAddr;
	@Column(name = "transfer_id")
	private String transferId;
	@Column(name = "transfer_status")
	private String transferStatus;
	@Column(name = "create_time")
	private java.util.Date createTime;

}