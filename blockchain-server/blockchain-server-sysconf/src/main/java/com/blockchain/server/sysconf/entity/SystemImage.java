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
@Table(name = "conf_system_image")
public class SystemImage extends BaseModel {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "file_url")
    private String fileUrl;
    @Column(name = "jump_url")
    private String jumpUrl;
    @Column(name = "rank")
    private Integer rank;
    @Column(name = "type")
    private String type;
    @Column(name = "status")
    private String status;
    @Column(name = "`group`")
    private String group;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "modify_time")
    private Date modifyTime;

}
