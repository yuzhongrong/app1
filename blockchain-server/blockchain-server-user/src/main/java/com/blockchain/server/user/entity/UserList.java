package com.blockchain.server.user.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UserList 用户黑白名单,数据传输类
 * @date 2019-02-21 13:37:18
 * @version 1.0
 */
@Table(name = "dapp_u_user_list")
@Data
public class UserList extends BaseModel {
	@Id
	@Column(name = "id")
	private String id;
	/**
	 * 用户id
	 */
	@Column(name = "user_id")
	private String userId;

	/**
	 * 名单类型
	 */
	@Column(name = "list_type")
	private String listType;

	/**
	 * 类型
	 */
	@Column(name = "type")
	private String type;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	private java.util.Date createTime;

}