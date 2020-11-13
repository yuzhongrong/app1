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
 * 关于我们信息表
 */
@Table(name = "conf_about_us")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AboutUs implements Serializable{

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "text_content")
    private String content;
    @Column(name = "languages")
    private String languages;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "modify_time")
    private Date modifyTime;


}
