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
 * 联系我们信息表
 */
@Table(name = "conf_contact_us")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactUs implements Serializable {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "contact_name")
    private String contactName;
    @Column(name = "contact_value")
    private String contactValue;
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
