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
 * CREATE TABLE `conf_project_center_star` (
 *   `id` varchar(36) NOT NULL COMMENT '项目点赞表',
 *   `project_id` varchar(36) DEFAULT NULL COMMENT '项目id',
 *   `user_id` varchar(36) DEFAULT NULL COMMENT '点赞人id',
 *   `create_time` datetime DEFAULT NULL,
 *   PRIMARY KEY (`id`)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 */
@Table(name = "conf_project_center_star")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCenterStar extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "project_id")
    private String projectId;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "create_time")
    private Date createTime;

}