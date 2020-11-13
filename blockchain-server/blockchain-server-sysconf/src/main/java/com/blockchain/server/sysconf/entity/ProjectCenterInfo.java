package com.blockchain.server.sysconf.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * CREATE TABLE `conf_project_center_info` (
 *   `id` varchar(36) NOT NULL,
 *   `currency_name` varchar(8) DEFAULT NULL COMMENT '数字货币类型，BTC、ETH、EOS',
 *   `order_by` int(11) DEFAULT NULL COMMENT '排序',
 *   `issue_time` varchar(20) DEFAULT NULL COMMENT '发行时间',
 *   `total_supply` varchar(255) DEFAULT NULL COMMENT '发行总量',
 *   `total_circulation` varchar(255) DEFAULT NULL COMMENT '流通量',
 *   `ico_amount` varchar(255) DEFAULT NULL COMMENT '众筹价格',
 *   `white_paper` varchar(255) DEFAULT NULL COMMENT '白皮书',
 *   `presentation` text COMMENT '介绍',
 *   `descr` text COMMENT '简介',
 *   `coin_url` varchar(255) DEFAULT NULL COMMENT '图标',
 *   `type` char(1) DEFAULT NULL COMMENT 'W 文件 L 链接',
 *   `status` char(1) DEFAULT NULL COMMENT '显示状态： 显示(Y)，隐藏(N)',
 *   `languages` varchar(10) DEFAULT NULL COMMENT '国际化标识',
 *   `classify_id` char(36) DEFAULT NULL COMMENT '分类id',
 *   `uccn` varchar(255) DEFAULT NULL COMMENT '官网',
 *   `create_time` datetime DEFAULT NULL,
 *   `modify_time` datetime DEFAULT NULL,
 *   PRIMARY KEY (`id`) USING BTREE
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 */
@Table(name = "conf_project_center_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCenterInfo extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "currency_name")
    private String currencyName;
    @Column(name = "status")
    private String status;
    @Column(name = "order_by")
    private Integer orderBy;
    @Column(name = "issue_time")
    private String issueTime;
    @Column(name = "total_supply")
    private String totalSupply;
    @Column(name = "total_circulation")
    private String totalCirculation;
    @Column(name = "ico_amount")
    private String icoAmount;
    @Column(name = "white_paper")
    private String whitePaper;
    @Column(name = "coin_url")
    private String coinUrl;
    @Column(name = "presentation")
    private String presentation;
    @Column(name = "descr")
    private String descr;
    @Column(name = "type")
    private String type;
    @Column(name = "languages")
    private String languages;
    @Column(name = "classify_id")
    private String classifyId;
    @Column(name = "uccn")
    private String uccn;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "modify_time")
    private Date modifyTime;

}