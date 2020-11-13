package com.blockchain.server.user.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UserLogin 数据传输类
 * @date 2019-02-21 13:37:18
 * @version 1.0
 */
@Table(name = "dapp_u_user_login")
@Data
public class UserLogin extends BaseModel {
	@Id
	@Column(name = "id")
	private String id;
	/**
	 * 用户id
	 */
	@Column(name = "user_id")
	private String userId;

	/**
	 * 密码
	 */
	@Column(name = "password")
	private String password;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	private java.util.Date createTime;

	/**
	 * 修改时间
	 */
	@Column(name = "modify_time")
	private java.util.Date modifyTime;

}