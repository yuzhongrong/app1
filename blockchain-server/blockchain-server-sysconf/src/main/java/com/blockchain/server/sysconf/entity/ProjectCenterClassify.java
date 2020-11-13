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
 * CREATE TABLE `conf_project_center_classify` (
 *   `id` varchar(36) NOT NULL COMMENT '项目中心类别',
 *   `name` varchar(255) DEFAULT NULL COMMENT '名称',
 *   `status` char(1) DEFAULT NULL COMMENT '显示状态： 显示(Y)，隐藏(N)',
 *   `create_time` datetime DEFAULT NULL,
 *   `modify_time` datetime DEFAULT NULL,
 *   PRIMARY KEY (`id`)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 */
@Table(name = "conf_project_center_classify")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCenterClassify extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "status")
    private String status;
    @Column(name = "languages")
    private String languages;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "modify_time")
    private Date modifyTime;

}