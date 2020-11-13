package com.blockchain.server.sysconf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 帮助中心表 conf_help_center
 * 
 * @author ruoyi
 * @date 2018-10-30
 */
@Table(name = "conf_help_center")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HelpCenter implements Serializable {

	@Id
	@Column(name = "id")
	private String id;
	@Column(name = "title")
	private String title;
	@Column(name = "content")
	private String content;
	@Column(name = "url")
	private String url;
	@Column(name = "rank")
	private Integer rank;
	@Column(name = "user_local")
	private String userLocal;
	@Column(name = "show_status")
	private Integer showStatus;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "modify_time")
	private Date modifyTime;

}
