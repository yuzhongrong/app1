package com.blockchain.server.user.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * AuthenticationApply 数据传输类
 * @date 2019-02-21 13:37:17
 * @version 1.0
 */
@Table(name = "dapp_u_authentication_apply")
@Data
public class AuthenticationApply extends BaseModel {
	@Id
	@Column(name = "id")
	private String id;
	/**
	 * 用户id
	 */
	@Column(name = "user_id")
	private String userId;
	/**
	 * 证件类型
	 */
	@Column(name = "type")
	private String type;
	/**
	 * 证件号
	 */
	@Column(name = "id_number")
	private String idNumber;
	/**
	 * 真是姓名
	 */
	@Column(name = "real_name")
	private String realName;
	/**
	 * 文件路径1
	 */
	@Column(name = "file_url1")
	private String fileUrl1;

	/**
	 * 文件路径2
	 */
	@Column(name = "file_url2")
	private String fileUrl2;
	/**
	 * 文件路径3
	 */
	@Column(name = "file_url3")
	private String fileUrl3;
	/**
	 * 审核状态
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