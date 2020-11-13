package com.blockchain.server.user.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UserMain 数据传输类
 * @date 2019-02-21 13:37:18
 * @version 1.0
 */
@Table(name = "dapp_u_user_main")
@Data
public class UserMain extends BaseModel {
	@Id
	@Column(name = "id")
	private String id;
	/**
	 * 昵称
	 */
	@Column(name = "nick_name")
	private String nickName;
	/**
	 * 手机国际区号
	 */
	@Column(name = "international_code")
	private String internationalCode;

	/**
	 * 手机国际区号
	 */
	@Column(name = "international")
	private String international;

	/**
	 * 手机号
	 */
	@Column(name = "mobile_phone")
	private String mobilePhone;

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