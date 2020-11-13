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
 * CREATE TABLE `conf_project_center_report` (
 *   `id` varchar(36) NOT NULL COMMENT '项目点赞表',
 *   `project_id` varchar(36) DEFAULT NULL COMMENT '项目id',
 *   `type` varchar(20) DEFAULT NULL COMMENT 'WEEK 周报 MONTH 月报 SPECIAL专题报告 FINANCIAL 财务报告',
 *   `create_time` datetime DEFAULT NULL,
 *   `title` varchar(255) DEFAULT NULL COMMENT '标题',
 *   `file_url` varchar(255) DEFAULT NULL COMMENT '文件地址',
 *   `status` char(1) DEFAULT NULL COMMENT '显示状态： 显示(Y)，隐藏(N)',
 *   PRIMARY KEY (`id`)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 */
@Table(name = "conf_project_center_report")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCenterReport extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "project_id")
    private String projectId;
    @Column(name = "type")
    private String type;
    @Column(name = "title")
    private String title;
    @Column(name = "file_url")
    private String fileUrl;
    @Column(name = "status")
    private String status;
    @Column(name = "create_time")
    private Date createTime;

}