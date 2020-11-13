package com.blockchain.server.sysconf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "conf_news")
public class News {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "title")
    private String title;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "type")
    private Integer type;
    @Column(name = "content")
    private String content;
    @Column(name = "url")
    private String url;
    @Column(name = "languages")
    private String languages;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "modify_time")
    private Date modifyTime;
}
