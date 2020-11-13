package com.blockchain.server.user.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UserLoginLog 数据传输类
 * @date 2019-02-21 13:37:18
 * @version 1.0
 */
@Table(name = "dapp_u_user_login_log")
@Data
public class UserLoginLog extends BaseModel {
	@Id
	@Column(name = "id")
	private String id;
	/**
	 * 用户id
	 */
	@Column(name = "user_id")
	private String userId;
	/**
	 * ip地址
	 */
	@Column(name = "ip_address")
	private String ipAddress;

	/**
	 * ip地址
	 */
	@Column(name = "status")
	private String status;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	private java.util.Date createTime;

}