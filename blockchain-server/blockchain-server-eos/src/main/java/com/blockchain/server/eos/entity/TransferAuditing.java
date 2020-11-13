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
@Table(name = "dapp_eos_transfer_auditing")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferAuditing extends BaseModel {
	@Id
	@Column(name = "id")
	private String id;
	@Column(name = "sys_user_id")
	private String sysUserId;
	@Column(name = "ip_addr")
	private String ipAddr;
	@Column(name = "transfer_id")
	private String transferId;
	@Column(name = "transfer_status")
	private Integer transferStatus;
	@Column(name = "create_time")
	private Date createTime;


}