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
 * app补丁版本表
 */
@Table(name = "conf_wgt_version")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WgtVersion implements Serializable{

    @Id
    @Column(name = "id")
    private String id;
    /**
     * 补丁版本号
     */
    @Column(name = "wgt_version")
    private String wgtVersion;
    /**
     * app补丁路径
     */
    @Column(name = "wgt_url")
    private String wgtUrl;
    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "modify_time")
    private Date modifyTime;


}
