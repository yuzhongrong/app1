package com.blockchain.server.sysconf.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author: Liusd
 * @create: 2019-03-25 13:36
 **/
@Data
@Table(name = "conf_system_notice")
public class SystemNotice extends BaseModel {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "title")
    private String title;
    @Column(name = "details")
    private String details;
    @Column(name = "jump_url")
    private String jumpUrl;
    @Column(name = "rank")
    private Integer rank;
    @Column(name = "status")
    private String status;
    @Column(name = "languages")
    private String languages;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "modify_time")
    private Date modifyTime;

}
