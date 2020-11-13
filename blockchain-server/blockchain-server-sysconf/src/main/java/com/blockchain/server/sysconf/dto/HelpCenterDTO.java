package com.blockchain.server.sysconf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HelpCenterDTO {

	private String id;
	private String title;
	private String content;
	private String url;
	private Integer rank;
	private String userLocal;
	private Integer showStatus;
	private Date createTime;
	private Date modifyTime;

}
