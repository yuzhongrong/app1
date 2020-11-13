package com.blockchain.server.user.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * HighAuthenticationApply 数据传输类
 * @date 2019-02-21 13:37:18
 * @version 1.0
 * 高级认证申请对象
 */
@Table(name = "dapp_u_high_authentication_apply")
@Data
public class HighAuthenticationApply extends BaseModel {
	@Id
	@Column(name = "id")
	private String id;
	/**
	 * 用户ID
	 */
	@Column(name = "user_id")
	private String userId;

	/**
	 * 文件路径
	 */
	@Column(name = "file_url")
	private String fileUrl;

	/**
	 * 认证状态
	 */
	@Column(name = "status")
	private String status;
	/**
	 * 审核备注
	 */
	@Column(name = "remark")
	private String remark;

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