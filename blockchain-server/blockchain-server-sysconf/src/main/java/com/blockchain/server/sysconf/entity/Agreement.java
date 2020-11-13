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
 *用户协议表
 */
@Table(name = "conf_user_agreement")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Agreement implements Serializable{

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "text_content")
    private String textContent;
    @Column(name = "languages")
    private String languages;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "modify_time")
    private Date modifyTime;
}
