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
 * app应用版本表
 */
@Table(name = "conf_version")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Version implements Serializable{

    @Id
    @Column(name = "id")
    private String id;
    /**
     * 版本号
     */
    @Column(name = "version")
    private String version;
    /**
     * app版本路径
     */
    @Column(name = "app_url")
    private String appUrl;
    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;
    /**
     * 是否强制更新：0-否，1-是
     */
    @Column(name = "compel")
    private Integer compel;
    /**
     * 设备 android | ios
     */
    @Column(name = "device")
    private String device;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "modify_time")
    private Date modifyTime;


}
